package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.CarRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.CarResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final OwnerService ownerService;
    private final RequestDtoMapper<CarRequestDto, Car> carRequestDtoMapper;
    private final ResponseDtoMapper<CarResponseDto, Car> carResponseDtoMapper;

    public CarController(CarService carService,
                         OwnerService ownerService,
                         RequestDtoMapper<CarRequestDto, Car> carRequestDtoMapper,
                         ResponseDtoMapper<CarResponseDto, Car> carResponseDtoMapper) {
        this.carService = carService;
        this.ownerService = ownerService;
        this.carRequestDtoMapper = carRequestDtoMapper;
        this.carResponseDtoMapper = carResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation("Save new car into Db")
    public CarResponseDto save(@RequestBody
                                   @ApiParam("Car data, with id of some params")
                                   CarRequestDto carRequestDto) {
        Car saved = carService.save(carRequestDtoMapper
                .mapToModel(carRequestDto));
        Owner carOwner = ownerService.getById(carRequestDto.getOwnerId());
        carOwner.getCars().add(saved);
        ownerService.save(carOwner);
        return carResponseDtoMapper.mapToDto(saved);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update information about car with id")
    public CarResponseDto update(@PathVariable
                                     @ApiParam("Id of car what you change")
                                     Long id,
                                 @RequestBody
                                    @ApiParam("New information of car")
                                    CarRequestDto carRequestDto) {
        Car car = carRequestDtoMapper.mapToModel(carRequestDto);
        car.setId(id);
        return carResponseDtoMapper.mapToDto(carService.save(car));
    }
}
