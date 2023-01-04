package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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
    private LocalDateTime acceptanceDate;
    @ManyToMany
    private List<Service> services;
    @ManyToMany
    private List<Article> articles;
    private Status status;
    private BigDecimal price;
    private LocalDate completeDate;


    public enum Status {
        ACCEPTED,
        IN_PROCESS,
        COMPLETED,
        FAIL_COMPLETED,
        PAID_FOR
    }
}
