package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.response.OwnerResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OwnerResponseDtoMapper implements ResponseDtoMapper<OwnerResponseDto, Owner> {
    @Override
    public OwnerResponseDto mapToDto(Owner model) {
        OwnerResponseDto responseDto = new OwnerResponseDto();
        responseDto.setId(model.getId());
        responseDto.setCarsIds(model.getCars().stream()
                .map(Car::getId)
                .collect(Collectors.toList()));
        responseDto.setOrdersIds(model.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
