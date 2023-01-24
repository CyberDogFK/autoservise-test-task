package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.ServiceRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.service.MasterService;
import org.springframework.stereotype.Component;

@Component
public class ServiceRequestDtoMapper implements RequestDtoMapper<ServiceRequestDto, Service> {
    private final MasterService masterService;

    public ServiceRequestDtoMapper(MasterService masterService) {
        this.masterService = masterService;
    }

    @Override
    public Service mapToModel(ServiceRequestDto serviceDto) {
        Service service = new Service();
        service.setName(serviceDto.getName());
        service.setMaster(masterService.getById(serviceDto.getMasterId()));
        service.setPrice(serviceDto.getPrice());
        service.setStatus(serviceDto.getStatus());
        return service;
    }
}
