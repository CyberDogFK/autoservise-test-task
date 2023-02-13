package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Service;
import java.util.List;

public interface ServiceService {
    List<Service> getAll();
    Service save(Service service);

    List<Service> saveAll(List<Service> services);

    Service getById(Long id);

    List<Service> getAllByIds(List<Long> ids);
}
