package com.mate.test.autoservice.mateautoservice.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequestDto {
    private List<Long> carsIds;
    private List<Long> ordersIds;
}
