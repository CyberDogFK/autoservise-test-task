package com.mate.test.autoservice.mateautoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MasterResponseDto {
    private Long id;
    private String name;
    private List<Long> solvedOrdersIds;
}
