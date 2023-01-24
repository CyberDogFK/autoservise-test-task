package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.CarRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import org.springframework.stereotype.Component;

@Component
public class CarRequestDtoMapper implements RequestDtoMapper<CarRequestDto, Car> {
    private final OwnerService ownerService;

    public CarRequestDtoMapper(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public Car mapToModel(CarRequestDto dto) {
        Car car = new Car();
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setRegNumber(dto.getRegNumber());
        car.setOwner(ownerService.getById(dto.getOwnerId()));
        return car;
    }
}
