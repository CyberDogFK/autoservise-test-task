package com.mate.test.autoservice.mateautoservice.service;

import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import java.util.List;

public interface OwnerService {
    List<Owner> getAll();

    Owner save(Owner owner);

    Owner getById(Long id);

    List<Order> getOrdersOfOwner(Long id);
}
