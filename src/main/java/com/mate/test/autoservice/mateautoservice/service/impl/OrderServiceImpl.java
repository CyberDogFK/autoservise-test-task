package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.repository.OrderRepository;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private static final double ARTICLE_DISCOUNT = 1; // in percent
    private static final double ORDER_DISCOUNT = 2; // in percent
    private final ArticleService articleService;
    private final OrderRepository orderRepository;
    private final OwnerService ownerService;

    public OrderServiceImpl(ArticleService articleService,
                            OrderRepository orderRepository,
                            OwnerService ownerService) {
        this.articleService = articleService;
        this.orderRepository = orderRepository;
        this.ownerService = ownerService;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find order with id " + id));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        return orderRepository.saveAll(orders);
    }

    @Override
    public Order addArticleForOrder(Article article, Long id) {
        //orderRepository.updateArticleForOrder(article, id);
        Order byId = getById(id);
        byId.getArticles().add(article);
        return orderRepository.save(byId);
    }

    @Override
    public BigDecimal getPriceOfOrder(Long id) {
        Order order = getById(id);
        return BigDecimal.valueOf(order.getServices().stream()
                .mapToDouble(s -> s.getPrice().doubleValue())
                .sum());
    }

    @Override
    public List<Order> getAllByIds(List<Long> ids) {
        List<Order> allById = orderRepository.findAllById(ids);
        if (allById.isEmpty()) {
            throw new RuntimeException("Can't find any order by ids " + ids);
        }
        return allById;
    }

    @Override
    public BigDecimal getPriceWithDiscount(List<Long> articleIds, Long ownerId) {
        double priceOfServices = getDiscountForOrders(ownerId);
        double priceOfArticles = getPriceOfArticles(articleIds);
        return BigDecimal.valueOf(priceOfServices + priceOfArticles);
    }

    private double getPriceOfArticles(List<Long> articleIds) {
        double sum = articleService.getAllByIds(articleIds)
                .stream()
                .mapToDouble(s -> s.getPrice().doubleValue())
                .sum();
        double percents = (ARTICLE_DISCOUNT * sum) / 100;
        sum -= percents;
        return sum;
    }

    private double getDiscountForOrders(Long ownerId) {
        int count = ownerService.getOrdersOfOwner(ownerId)
                .size();
        double percents = (ORDER_DISCOUNT * count) / 100;
        count -= percents;
        return count;
    }
}
