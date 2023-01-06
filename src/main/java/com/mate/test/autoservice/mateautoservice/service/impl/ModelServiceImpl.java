package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Model;
import com.mate.test.autoservice.mateautoservice.repository.ModelRepository;
import com.mate.test.autoservice.mateautoservice.service.ModelService;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public Model getById(Long id) {
        return modelRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find model with id " + id));
    }
}
