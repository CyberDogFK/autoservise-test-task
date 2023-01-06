package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.OwnerRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
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
        owner.setCars(carService.getAllByIds(dto.getCarsIds()));
        owner.setOrders(orderService.getAllByIds(dto.getOrdersIds()));
        return owner;
    }
}
