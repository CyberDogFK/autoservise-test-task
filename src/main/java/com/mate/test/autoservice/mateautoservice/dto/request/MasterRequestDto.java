package com.mate.test.autoservice.mateautoservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MasterRequestDto {
    private String name;
    private List<Long> solvedOrdersIds;
}
