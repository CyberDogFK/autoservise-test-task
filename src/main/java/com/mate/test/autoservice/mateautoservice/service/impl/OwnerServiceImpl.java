package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.repository.OwnerRepository;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
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
