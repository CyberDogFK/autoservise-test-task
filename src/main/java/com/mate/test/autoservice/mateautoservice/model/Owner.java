package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(generator = "owner_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "owner_id_seq",
            sequenceName = "owner_id_seq",
            allocationSize = 1)    private Long id;
    @OneToMany
    private List<Car> cars;
    @OneToMany
    private List<Order> orders;
}