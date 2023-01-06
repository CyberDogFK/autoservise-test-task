package com.mate.test.autoservice.mateautoservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ArticleRequestDto {
    private String name;
    private BigDecimal price;
}
