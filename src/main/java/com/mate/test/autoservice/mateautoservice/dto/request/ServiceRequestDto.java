package com.mate.test.autoservice.mateautoservice.dto.request;

import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceRequestDto {
    private String name;
    private Long masterId;
    private BigDecimal price;
    private ServiceStatus status;
}
