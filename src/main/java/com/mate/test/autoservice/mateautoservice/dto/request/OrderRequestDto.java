package com.mate.test.autoservice.mateautoservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Long carId;
    private String problemDescription;
    private LocalDate acceptanceDate;
    private List<Long> servicesIds;
    private List<Long> articlesIds;
    private String status;
    private BigDecimal price;
    private LocalDate completedDate;
}
