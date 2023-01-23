package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.OwnerRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.CarResponseDto;
import com.mate.test.autoservice.mateautoservice.dto.response.OrderResponseDto;
import com.mate.test.autoservice.mateautoservice.dto.response.OwnerResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import com.mate.test.autoservice.mateautoservice.service.mapper.CarResponseDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.OrderResponseDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.OwnerRequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.OwnerResponseDtoMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;
    private final OwnerRequestDtoMapper ownerRequestDtoMapper;
    private final OwnerResponseDtoMapper ownerResponseDtoMapper;
    private final OrderResponseDtoMapper orderResponseDtoMapper;
    private final CarResponseDtoMapper carResponseDtoMapper;

    public OwnerController(OwnerService ownerService,
                           OwnerRequestDtoMapper ownerRequestDtoMapper,
                           OwnerResponseDtoMapper ownerResponseDtoMapper, OrderResponseDtoMapper orderResponseDtoMapper, CarResponseDtoMapper carResponseDtoMapper) {
        this.ownerService = ownerService;
        this.ownerRequestDtoMapper = ownerRequestDtoMapper;
        this.ownerResponseDtoMapper = ownerResponseDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
        this.carResponseDtoMapper = carResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation("Creating owner")
    public OwnerResponseDto create(@RequestBody OwnerRequestDto ownerRequestDto) {
        return ownerResponseDtoMapper.mapToDto(
                ownerService.save(
                        ownerRequestDtoMapper.mapToModel(ownerRequestDto)));
    }

    @PutMapping("/{id}")
    public OwnerResponseDto update(@PathVariable Long id, @RequestBody OwnerRequestDto ownerRequestDto) {
        Owner owner = ownerRequestDtoMapper.mapToModel(ownerRequestDto);
        owner.setId(id);
        return ownerResponseDtoMapper.mapToDto(ownerService.save(owner));
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponseDto> getOrdersOf(@PathVariable Long id) {
        return ownerService.getOrdersOfOwner(id).stream()
                .map(orderResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/cars")
    public List<CarResponseDto> getOwnerCars(@PathVariable Long id) {
        return ownerService.getById(id).getCars().stream()
                .map(carResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
