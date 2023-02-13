package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Article;
import java.util.List;

public interface ArticleService {
    Article get(Long id);
    List<Article> getAll();
    List<Article> getAllByIds(List<Long> ids);

    Article save(Article article);
}
