package com.mate.test.autoservice.mateautoservice.repository;

import com.mate.test.autoservice.mateautoservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
