package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.CarRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.CarResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<CarResponseDto> getAll() {
        return carService.getAll().stream()
                .map(carResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CarResponseDto get(@PathVariable Long id) {
        return carResponseDtoMapper.mapToDto(carService.getById(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Save new car into Db")
    public CarResponseDto save(@RequestBody
                                   @Parameter(description = "Car data, with id of some params")
                                   CarRequestDto carRequestDto) {
        Car saved = carService.save(carRequestDtoMapper
                .mapToModel(carRequestDto));
        Owner carOwner = ownerService.getById(carRequestDto.getOwnerId());
        carOwner.getCars().add(saved);
        ownerService.save(carOwner);
        return carResponseDtoMapper.mapToDto(saved);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(description = "Update information about car with id")
    public CarResponseDto update(@PathVariable
                                     @Parameter(description = "Id of car what you change")
                                     Long id,
                                 @RequestBody
                                    @Parameter(description = "New information of car")
                                    CarRequestDto carRequestDto) {
        Car car = carRequestDtoMapper.mapToModel(carRequestDto);
        car.setId(id);
        return carResponseDtoMapper.mapToDto(carService.save(car));
    }
}
