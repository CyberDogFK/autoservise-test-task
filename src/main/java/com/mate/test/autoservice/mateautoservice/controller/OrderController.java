package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ArticleRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.request.OrderRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.ArticleResponseDto;
import com.mate.test.autoservice.mateautoservice.dto.response.OrderResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper;
    private final RequestDtoMapper<OrderRequestDto, Order> orderRequestDtoMapper;
    private final RequestDtoMapper<ArticleRequestDto, Article> articleRequestDtoMapper;
    private final ResponseDtoMapper<ArticleResponseDto, Article> articleResponseDtoMapper;

    public OrderController(OrderService orderService, ResponseDtoMapper<OrderResponseDto,
            Order> orderResponseDtoMapper, RequestDtoMapper<OrderRequestDto,
            Order> orderRequestDtoMapper, RequestDtoMapper<ArticleRequestDto,
            Article> articleRequestDtoMapper, ResponseDtoMapper<ArticleResponseDto,
            Article> articleResponseDtoMapper) {
        this.orderService = orderService;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
        this.orderRequestDtoMapper = orderRequestDtoMapper;
        this.articleRequestDtoMapper = articleRequestDtoMapper;
        this.articleResponseDtoMapper = articleResponseDtoMapper;
    }

    @PostMapping
    public OrderResponseDto create(@RequestBody OrderRequestDto orderRequestDto) {
        return orderResponseDtoMapper.mapToDto(
                orderService.save(
                        orderRequestDtoMapper.mapToModel(orderRequestDto)));
    }

    @PostMapping("/{id}/article")
    public OrderResponseDto addArticleToOrder(@PathVariable Long id,
                                              @RequestBody List<ArticleRequestDto> articleRequestDtoList) {
        articleRequestDtoList.stream()
                .map(articleRequestDtoMapper::mapToModel)
                        .forEach(a -> orderService.addArticleForOrder(a, id));
        return orderResponseDtoMapper.mapToDto(orderService.getById(id));
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id, @RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderRequestDtoMapper.mapToModel(orderRequestDto);
        order.setId(id);
        return orderResponseDtoMapper.mapToDto(orderService.save(order));
    }

    @PutMapping("/{id}/status")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                         @RequestParam OrderStatus orderStatus) {
        Order order = orderService.getById(id);
        order.setStatus(orderStatus);
        return orderResponseDtoMapper.mapToDto(orderService.save(order));
    }

    @GetMapping("/{id}/price")
    public BigDecimal getPriceOf(@PathVariable Long id) {
        return orderService.getPriceOfOrder(id);
    }
}
