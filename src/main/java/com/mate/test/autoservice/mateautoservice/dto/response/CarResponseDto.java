package com.mate.test.autoservice.mateautoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {
    private Long id;
    private Long brandId;
    private Long modelId;
    private Integer year;
    private String regNumber;
    private Long ownerId;
}
