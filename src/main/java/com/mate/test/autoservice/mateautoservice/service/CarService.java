package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Car;
import java.util.List;

public interface CarService {
    List<Car> getAll();
    Car save(Car car);

    Car getById(Long id);

    List<Car> getAllByIds(List<Long> ids);
}
