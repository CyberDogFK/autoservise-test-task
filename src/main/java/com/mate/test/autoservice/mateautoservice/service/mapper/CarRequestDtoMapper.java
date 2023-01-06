package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.CarRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.service.BrandService;
import com.mate.test.autoservice.mateautoservice.service.ModelService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CarRequestDtoMapper implements RequestDtoMapper<CarRequestDto, Car> {
    private final BrandService brandService;
    private final ModelService modelService;
    private final OwnerService ownerService;

    public CarRequestDtoMapper(BrandService brandService,
                               ModelService modelService,
                               OwnerService ownerService) {
        this.brandService = brandService;
        this.modelService = modelService;
        this.ownerService = ownerService;
    }

    @Override
    public Car mapToModel(CarRequestDto dto) {
        Car car = new Car();
        car.setBrand(brandService.getById(dto.getBrandId()));
        car.setModel(modelService.getById(dto.getModelId()));
        car.setYear(dto.getYear());
        car.setRegNumber(dto.getRegNumber());
        car.setOwner(ownerService.getById(dto.getOwnerId()));
        return car;
    }
}
