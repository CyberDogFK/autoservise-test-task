package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "order_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_id_seq",
            sequenceName = "order_id_seq",
            allocationSize = 1)
    private Long id;
    @ManyToOne
    private Car car;
    private String problemDescription;
    private LocalDate acceptanceDate;
    @ManyToMany
    private List<Service> services;
    @ManyToMany
    private List<Article> articles;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    private BigDecimal price;
    private LocalDate completeDate;

    public Order(Car car, String problemDescription,
                 LocalDate acceptanceDate, List<Service> services,
                 List<Article> articles, OrderStatus status, LocalDate completeDate) {
        this.car = car;
        this.problemDescription = problemDescription;
        this.acceptanceDate = acceptanceDate;
        this.services = services;
        this.articles = articles;
        this.status = status;
        this.completeDate = completeDate;
    }

    public Order(Car car, String problemDescription, LocalDate acceptanceDate,
                 List<Service> services, List<Article> articles,
                 OrderStatus status, BigDecimal price, LocalDate completeDate) {
        this.car = car;
        this.problemDescription = problemDescription;
        this.acceptanceDate = acceptanceDate;
        this.services = services;
        this.articles = articles;
        this.status = status;
        this.price = price;
        this.completeDate = completeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(problemDescription, order.problemDescription)
                && Objects.equals(acceptanceDate, order.acceptanceDate)
                && status == order.status
                && Objects.equals(price, order.price)
                && Objects.equals(completeDate, order.completeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, problemDescription, acceptanceDate, status, price, completeDate);
    }
}
