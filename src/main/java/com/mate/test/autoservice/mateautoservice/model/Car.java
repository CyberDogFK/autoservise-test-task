package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(generator = "car_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "car_id_seq",
            sequenceName = "car_id_seq",
            allocationSize = 1)
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String regNumber;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Owner owner;
}
