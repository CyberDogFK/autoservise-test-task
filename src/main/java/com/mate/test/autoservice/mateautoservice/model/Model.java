package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Model {
    @Id
    @GeneratedValue(generator = "model_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "model_id_seq",
            sequenceName = "model_id_seq",
            allocationSize = 1)    private Long id;
    private String name;
}