package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.OrderRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.OrderResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.MasterService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ArticleService articleService;
    private final ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper;
    private final RequestDtoMapper<OrderRequestDto, Order> orderRequestDtoMapper;
    private final CarService carService;
    private final OwnerService ownerService;
    private final MasterService masterService;

    public OrderController(OrderService orderService, ArticleService articleService,
            ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper,
            RequestDtoMapper<OrderRequestDto, Order> orderRequestDtoMapper,
            CarService carService, OwnerService ownerService,
            MasterService masterService) {
        this.orderService = orderService;
        this.articleService = articleService;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
        this.orderRequestDtoMapper = orderRequestDtoMapper;
        this.carService = carService;
        this.ownerService = ownerService;
        this.masterService = masterService;
    }

    @GetMapping
    public List<OrderResponseDto> getAll() {
        return orderService.getAll().stream()
                .map(orderResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable Long id) {
        return orderResponseDtoMapper.mapToDto(
                orderService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Creating new order ")
    public OrderResponseDto create(@RequestBody
            @Parameter(description = "Take carId, problemDescription string, "
                    + "acceptance date (in pattern yyyy-MM-dd, "
                    + "serviceIds array, articleIds array, Status (orderStatus), "
                    + "completeDate (in pattern yyyy-MM-dd)")
                                       OrderRequestDto orderRequestDto) {
        Owner owner = carService.getById(orderRequestDto.getCarId())
                .getOwner();
        Order order = orderRequestDtoMapper.mapToModel(orderRequestDto);
        order.setPrice(orderService.getPriceWithDiscount(order));
        List<Order> orders = owner.getOrders();
        orders.add(order);
        owner.setOrders(orders);
        return orderResponseDtoMapper.mapToDto(
                orderService.save(order));
    }

    @PostMapping("/{id}/article")
    @Operation(description = "Add new articles to order")
    public OrderResponseDto addArticleToOrder(@PathVariable
                                                  @Parameter(description = "Order id")
                                                  Long id,
                                              @RequestBody
                                                  @Parameter(description =
                                                          "Array of article ids for adding")
                                                  List<Long> articleIds) {
        Order order = orderService.getById(id);
        List<Article> articlesToSave = articleService.getAllByIds(articleIds);
        return orderResponseDtoMapper.mapToDto(orderService.save(
                orderService.addArticleForOrder(articlesToSave, order)));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(description = "Change specific order")
    public OrderResponseDto update(@PathVariable
                                       @Parameter(description = "Id of changeable order")
                                       Long id,
                                   @RequestBody
                                       @Parameter(description = "Take new params of object,"
                                               + " same as in post request")
                                       OrderRequestDto orderRequestDto) {
        Order order = orderRequestDtoMapper.mapToModel(orderRequestDto);
        order.setId(id);
        BigDecimal priceWithDiscount = orderService.getPriceWithDiscount(order);
        order.setPrice(priceWithDiscount);
        System.err.println(order.getCar());
        return orderResponseDtoMapper.mapToDto(orderService.save(order));
    }

    @CrossOrigin
    @PutMapping("/{id}/status")
    @Operation(description = "Change status of order")
    public OrderResponseDto updateStatus(@PathVariable
                                             @Parameter(description = "Id of order")
                                             Long id,
                                         @RequestParam
                                             @Parameter(description =
                                                     "One of orderStatus (ACCEPTED, "
                                                 + "IN_PROCESS, "
                                                 + "COMPLETED, "
                                                 + "FAIL_COMPLETED, "
                                                 + "PAID_FOR)")
                                             OrderStatus orderStatus) {
        Order order = orderService.getById(id);
        order.setStatus(orderStatus);
        if (orderStatus.equals(OrderStatus.COMPLETED)) {
            order.getServices().stream()
                    .map(Service::getMaster)
                    .forEach(m -> {
                        m.getSolvedOrders().add(order);
                        masterService.save(m);
                    });
        }
        return orderResponseDtoMapper.mapToDto(orderService.save(order));
    }

    @GetMapping("/{id}/price")
    @Operation(description = "Return price of order")
    public BigDecimal getPriceOf(@PathVariable
                                     @Parameter(description = "Id of order")
                                     Long id) {
        return orderService.getById(id).getPrice();
    }
}
