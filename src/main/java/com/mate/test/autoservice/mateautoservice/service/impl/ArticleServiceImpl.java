package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.repository.ArticleRepository;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article get(Long id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No article with id " + id)
        );
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getAllByIds(List<Long> ids) {
        return articleRepository.findAllById(ids);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

}
