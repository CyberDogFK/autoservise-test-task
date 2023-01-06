package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.response.MasterResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MasterResponseDtoMapper implements ResponseDtoMapper<MasterResponseDto, Master>{
    @Override
    public MasterResponseDto mapToDto(Master model) {
        MasterResponseDto masterDto = new MasterResponseDto();
        masterDto.setId(model.getId());
        masterDto.setName(model.getName());
        masterDto.setSolvedOrdersIds(model.getSolvedOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList()));
        return masterDto;
    }
}
