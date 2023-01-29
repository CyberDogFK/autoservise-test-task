package com.mate.test.autoservice.mateautoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    public Master(String name, List<Order> solvedOrders) {
        this.name = name;
        this.solvedOrders = solvedOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Master master = (Master) o;
        return Objects.equals(id, master.id)
                && Objects.equals(name, master.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
