package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Master;
import java.math.BigDecimal;

public interface MasterService {
    Master save(Master master);

    Master getById(Long id);

    BigDecimal paidForServicesForMaster(Long id);
}
