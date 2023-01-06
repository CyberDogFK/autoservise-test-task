package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Brand;
import com.mate.test.autoservice.mateautoservice.repository.BrandRepository;
import com.mate.test.autoservice.mateautoservice.service.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find brand by id " + id));
    }
}
