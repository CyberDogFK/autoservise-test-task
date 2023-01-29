package com.mate.test.autoservice.mateautoservice.dto.request;

import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {
    private String name;
    private Long masterId;
    private BigDecimal price;
    private ServiceStatus status;
}
