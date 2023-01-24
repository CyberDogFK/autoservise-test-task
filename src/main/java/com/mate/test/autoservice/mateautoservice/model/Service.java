package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

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
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
}
