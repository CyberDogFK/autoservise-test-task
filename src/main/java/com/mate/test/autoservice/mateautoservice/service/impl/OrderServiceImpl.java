package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.repository.OrderRepository;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private static final double ARTICLE_DISCOUNT = 1; // in percent
    private static final double ORDER_DISCOUNT = 2; // in percent
    private final ArticleService articleService;
    private final OrderRepository orderRepository;
    private final OwnerService ownerService;
    private final CarService carService;

    public OrderServiceImpl(ArticleService articleService,
                            OrderRepository orderRepository,
                            OwnerService ownerService, CarService carService) {
        this.articleService = articleService;
        this.orderRepository = orderRepository;
        this.ownerService = ownerService;
        this.carService = carService;
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
    public Order addArticleForOrder(List<Article> articles, Order order) {
        articles.forEach(a -> order.getArticles().add(a));
        return order;
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
    public BigDecimal getPriceWithDiscount(Order order) {
        Owner owner = carService.getById(order.getCar().getId())
                .getOwner();
        double priceOfServices = getDiscountForOrders(owner.getId());
        double priceOfArticles = getPriceOfArticles(order.getArticles().stream()
                .map(Article::getId)
                .collect(Collectors.toList()));
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
