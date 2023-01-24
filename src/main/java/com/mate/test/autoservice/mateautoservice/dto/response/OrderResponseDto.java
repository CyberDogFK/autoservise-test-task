package com.mate.test.autoservice.mateautoservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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
