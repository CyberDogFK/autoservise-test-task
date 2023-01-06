package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.repository.OwnerRepository;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Owner getById(Long id) {
        return ownerRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find owner with id " + id));
    }

    @Override
    public List<Order> getOrdersOfOwner(Long id) {
        return getById(id).getOrders();
    }
}
