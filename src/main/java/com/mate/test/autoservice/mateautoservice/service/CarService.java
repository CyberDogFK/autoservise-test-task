package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Car;
import java.util.List;

public interface CarService {
    Car save(Car car);

    Car getById(Long id);

    List<Car> getAllByIds(List<Long> ids);
}
