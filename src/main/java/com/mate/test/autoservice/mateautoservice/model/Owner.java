package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.List;
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
public class Owner {
    @Id
    @GeneratedValue(generator = "owner_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "owner_id_seq",
            sequenceName = "owner_id_seq",
            allocationSize = 1)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;
    @OneToMany
    private List<Order> orders;

    public Owner(List<Car> cars, List<Order> orders) {
        this.cars = cars;
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
