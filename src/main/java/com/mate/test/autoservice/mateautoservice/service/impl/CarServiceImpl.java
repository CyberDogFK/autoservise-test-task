package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.repository.CarRepository;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find car with id " + id));
    }

    @Override
    public List<Car> getAllByIds(List<Long> ids) {
        return carRepository.findAllById(ids);
    }
}
