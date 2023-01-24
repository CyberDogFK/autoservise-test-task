package com.mate.test.autoservice.mateautoservice.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequestDto {
    private String name;
    private BigDecimal price;
}
