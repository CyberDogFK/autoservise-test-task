package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Car(String brand, String model, Integer year, String regNumber, Owner owner) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.regNumber = regNumber;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(id, car.id)
                && Objects.equals(brand, car.brand)
                && Objects.equals(model, car.model)
                && Objects.equals(year, car.year)
                && Objects.equals(regNumber, car.regNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, year, regNumber);
    }
}
