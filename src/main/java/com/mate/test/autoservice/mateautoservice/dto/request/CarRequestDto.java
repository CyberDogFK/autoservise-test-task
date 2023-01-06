package com.mate.test.autoservice.mateautoservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequestDto {
    private Long brandId;
    private Long modelId;
    private Integer year;
    private String regNumber;
    private Long ownerId;
}
