package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.ArticleRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleRequestDtoMapper implements RequestDtoMapper<ArticleRequestDto, Article> {
    @Override
    public Article mapToModel(ArticleRequestDto dto) {
        Article article = new Article();
        article.setName(dto.getName());
        article.setPrice(dto.getPrice());
        return article;
    }
}
