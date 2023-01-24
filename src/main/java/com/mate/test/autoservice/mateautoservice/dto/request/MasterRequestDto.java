package com.mate.test.autoservice.mateautoservice.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterRequestDto {
    private String name;
    private List<Long> solvedOrdersIds;
}
