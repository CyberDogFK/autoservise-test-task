package com.mate.test.autoservice.mateautoservice.dto.response;

import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Order;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
public class OwnerResponseDto {
    private Long id;
    private List<Long> carsIds;
    private List<Long> ordersIds;
}
