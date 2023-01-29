package com.mate.test.autoservice.mateautoservice.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDto {
    private String name;
    private BigDecimal price;
}
