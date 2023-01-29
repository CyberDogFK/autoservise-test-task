package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Article {
    @Id
    @GeneratedValue(generator = "article_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "article_id_seq",
            sequenceName = "article_id_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private BigDecimal price;

    public Article(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
