package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order getById(Long id);

    Order save(Order order);

    List<Order> saveAll(List<Order> orders);

    List<Order> getAllByIds(List<Long> ids);

    Order addArticleForOrder(List<Article> articles, Order order);

    BigDecimal getPriceWithDiscount(Order order);
}
