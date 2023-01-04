package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(generator = "article_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "article_id_seq",
            sequenceName = "article_id_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private BigDecimal price;
}
