package com.mate.test.autoservice.mateautoservice.dto.response;

import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDto {
    private Long id;
    private String name;
    private Long masterId;
    private BigDecimal price;
    private ServiceStatus status;
}
