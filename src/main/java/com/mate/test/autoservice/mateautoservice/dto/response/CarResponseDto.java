package com.mate.test.autoservice.mateautoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String regNumber;
    private Long ownerId;
}
