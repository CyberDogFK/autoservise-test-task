package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Master {
    @Id
    @GeneratedValue(generator = "master_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "master_id_seq",
            sequenceName = "master_id_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @ManyToMany
    private List<Order> solvedOrders;
}
