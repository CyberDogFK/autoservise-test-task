package com.mate.test.autoservice.mateautoservice.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
