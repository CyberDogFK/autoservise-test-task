package com.mate.test.autoservice.mateautoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ArticleResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
