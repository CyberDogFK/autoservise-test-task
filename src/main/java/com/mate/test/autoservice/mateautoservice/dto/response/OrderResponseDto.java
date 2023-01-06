package com.mate.test.autoservice.mateautoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private String problemDescription;
    private LocalDate acceptanceDate;
    private List<Long> servicesIds;
    private List<Long> articlesIds;
    private String status;
    private BigDecimal price;
    private LocalDate completedDate;
}
