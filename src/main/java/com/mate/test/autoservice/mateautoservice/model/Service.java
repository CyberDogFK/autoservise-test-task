package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(generator = "service_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "service_id_seq",
            sequenceName = "service_id_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne
    private Master master;
    private BigDecimal price;
    private Status status;

    public enum Status {
        PAID,
        NON_PAID
    }
}
