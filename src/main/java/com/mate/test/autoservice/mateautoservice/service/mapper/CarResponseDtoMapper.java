package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.response.CarResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CarResponseDtoMapper implements ResponseDtoMapper<CarResponseDto, Car> {
    @Override
    public CarResponseDto mapToDto(Car car) {
        CarResponseDto responseDto = new CarResponseDto();
        responseDto.setId(car.getId());
        responseDto.setBrandId(car.getBrand().getId());
        responseDto.setModelId(car.getModel().getId());
        responseDto.setYear(car.getYear());
        responseDto.setRegNumber(car.getRegNumber());
        responseDto.setOwnerId(car.getOwner().getId());
        return responseDto;
    }
}
