package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.OwnerRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OwnerRequestDtoMapper implements RequestDtoMapper<OwnerRequestDto, Owner> {
    private final CarService carService;
    private final OrderService orderService;

    public OwnerRequestDtoMapper(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @Override
    public Owner mapToModel(OwnerRequestDto dto) {
        Owner owner = new Owner();
        if (dto.getCarsIds() != null) {
            owner.setCars(carService.getAllByIds(dto.getCarsIds()));
        } else {
            owner.setCars(List.of());
        }
        if (dto.getOrdersIds() != null) {
            owner.setOrders(orderService.getAllByIds(dto.getOrdersIds()));
        } else {
            owner.setOrders(List.of());
        }
        return owner;
    }
}
