package com.mate.test.autoservice.mateautoservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDto {
    private String brand;
    private String model;
    private Integer year;
    private String regNumber;
    private Long ownerId;
}
