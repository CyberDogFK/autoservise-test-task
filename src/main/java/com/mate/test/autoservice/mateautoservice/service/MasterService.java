package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Master;
import java.math.BigDecimal;
import java.util.List;

public interface MasterService {
    List<Master> getAll();

    Master save(Master master);

    Master getById(Long id);

    BigDecimal paidForServicesForMaster(Long id);
}
