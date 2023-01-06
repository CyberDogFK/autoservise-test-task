package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.response.ArticleResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleResponseDtoMapper implements ResponseDtoMapper<ArticleResponseDto, Article> {
    @Override
    public ArticleResponseDto mapToDto(Article model) {
        ArticleResponseDto articleResponseDto = new ArticleResponseDto();
        articleResponseDto.setId(model.getId());
        articleResponseDto.setName(model.getName());
        articleResponseDto.setPrice(model.getPrice());
        return articleResponseDto;
    }
}
