package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Master;

public interface MasterService {
    Master save(Master master);
    Master getById(Long id);

    Double paidForServicesForMaster(Long id);
}
