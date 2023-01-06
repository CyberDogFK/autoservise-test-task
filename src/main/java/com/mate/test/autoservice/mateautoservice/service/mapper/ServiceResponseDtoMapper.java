package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.response.ServiceResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceResponseDtoMapper implements ResponseDtoMapper<ServiceResponseDto, Service>{
    @Override
    public ServiceResponseDto mapToDto(Service model) {
        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(model.getId());
        responseDto.setName(model.getName());
        responseDto.setMasterId(model.getMaster().getId());
        responseDto.setPrice(model.getPrice());
        responseDto.setStatus(model.getStatus());
        return responseDto;
    }
}
