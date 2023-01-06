package com.mate.test.autoservice.mateautoservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OwnerRequestDto {
    private List<Long> carsIds;
    private List<Long> ordersIds;
}
