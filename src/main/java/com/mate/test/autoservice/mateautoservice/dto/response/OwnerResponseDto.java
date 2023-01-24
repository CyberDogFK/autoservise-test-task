package com.mate.test.autoservice.mateautoservice.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerResponseDto {
    private Long id;
    private List<Long> carsIds;
    private List<Long> ordersIds;
}
