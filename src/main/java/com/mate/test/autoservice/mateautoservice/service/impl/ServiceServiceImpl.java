package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.repository.ServiceRepository;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Service> saveAll(List<Service> services) {
        return serviceRepository.saveAll(services);
    }

    @Override
    public Service getById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find service with id " + id));
    }

    @Override
    public List<Service> getAllByIds(List<Long> ids) {
        List<Service> allById = serviceRepository.findAllById(ids);
        if (allById.isEmpty()) {
            throw new RuntimeException("Can't find any service from ids " + ids);
        }
        return allById;
    }
}
