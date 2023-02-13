package com.mate.test.autoservice.mateautoservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long carId;
    private String problemDescription;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate acceptanceDate;
    private List<Long> servicesIds;
    private List<Long> articlesIds;
    private OrderStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate completedDate;
}
