package com.mate.test.autoservice.mateautoservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate acceptanceDate;
    private List<Long> servicesIds;
    private List<Long> articlesIds;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate completedDate;
}
