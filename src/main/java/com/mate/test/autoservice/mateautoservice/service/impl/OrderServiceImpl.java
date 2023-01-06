package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.repository.OrderRepository;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        return orderRepository.updateArticleForOrder(article, id);
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
}
