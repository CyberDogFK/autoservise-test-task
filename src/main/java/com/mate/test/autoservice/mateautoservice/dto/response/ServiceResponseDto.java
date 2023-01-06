package com.mate.test.autoservice.mateautoservice.dto.response;

import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ServiceResponseDto {
    private Long id;
    private String name;
    private Long masterId;
    private BigDecimal price;
    private ServiceStatus status;
}
