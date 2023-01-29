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

    public Service(String name, Master master, BigDecimal price, ServiceStatus status) {
        this.name = name;
        this.master = master;
        this.price = price;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Service service = (Service) o;
        return Objects.equals(id, service.id)
                && Objects.equals(name, service.name)
                && Objects.equals(price, service.price)
                && status == service.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, status);
    }
}
