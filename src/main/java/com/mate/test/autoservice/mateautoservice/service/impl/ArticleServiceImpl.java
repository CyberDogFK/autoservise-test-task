package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.repository.ArticleRepository;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAllByIds(List<Long> ids) {
        List<Article> allById = articleRepository.findAllById(ids);
        if (allById.isEmpty()) {
            throw new RuntimeException("Can't find any article from ids " + ids);
        }
        return allById;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }
}
