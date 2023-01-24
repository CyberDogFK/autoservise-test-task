package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.OrderRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestDtoMapper implements RequestDtoMapper<OrderRequestDto, Order> {
    private final CarService carService;
    private final ServiceService serviceService;
    private final ArticleService articleService;

    public OrderRequestDtoMapper(CarService carService,
                                 ServiceService serviceService,
                                 ArticleService articleService) {
        this.carService = carService;
        this.serviceService = serviceService;
        this.articleService = articleService;
    }

    @Override
    public Order mapToModel(OrderRequestDto dto) {
        Order order = new Order();
        order.setCar(carService.getById(dto.getCarId()));
        order.setProblemDescription(dto.getProblemDescription());
        order.setAcceptanceDate(dto.getAcceptanceDate());
        order.setServices(serviceService.getAllByIds(dto.getServicesIds()));
        order.setArticles(articleService.getAllByIds(dto.getArticlesIds()));
        order.setStatus(OrderStatus.valueOf(dto.getStatus()));
        order.setCompleteDate(dto.getCompletedDate());
        return order;
    }
}
