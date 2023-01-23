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

    Order addArticleForOrder(Article article, Long id);

    BigDecimal getPriceOfOrder(Long id);

    BigDecimal getPriceWithDiscount(List<Long> articleIds, Long ownerId);
}
