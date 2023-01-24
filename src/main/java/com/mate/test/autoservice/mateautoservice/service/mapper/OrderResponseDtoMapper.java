package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.response.OrderResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.Service;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseDtoMapper implements ResponseDtoMapper<OrderResponseDto, Order> {
    @Override
    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setCarId(order.getCar().getId());
        responseDto.setProblemDescription(order.getProblemDescription());
        responseDto.setAcceptanceDate(order.getAcceptanceDate());
        responseDto.setServicesIds(order.getServices().stream()
                .map(Service::getId)
                .collect(Collectors.toList()));
        responseDto.setArticlesIds(order.getArticles().stream()
                .map(Article::getId)
                .collect(Collectors.toList()));
        responseDto.setStatus(order.getStatus().toString());
        responseDto.setPrice(order.getPrice());
        responseDto.setCompletedDate(order.getCompleteDate());
        return responseDto;
    }
}
