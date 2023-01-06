package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ServiceRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.ServiceResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServiceService serviceService;
    private final RequestDtoMapper<ServiceRequestDto, Service> serviceRequestDtoMapper;
    private final ResponseDtoMapper<ServiceResponseDto, Service> serviceResponseDtoMapper;

    public ServiceController(ServiceService serviceService,
                             RequestDtoMapper<ServiceRequestDto, Service> serviceRequestDtoMapper,
                             ResponseDtoMapper<ServiceResponseDto, Service> serviceResponseDtoMapper) {
        this.serviceService = serviceService;
        this.serviceRequestDtoMapper = serviceRequestDtoMapper;
        this.serviceResponseDtoMapper = serviceResponseDtoMapper;
    }

    @PostMapping
    public ServiceResponseDto create(@RequestBody ServiceRequestDto serviceRequestDto) {
        return serviceResponseDtoMapper.mapToDto(
                serviceService.save(
                        serviceRequestDtoMapper.mapToModel(serviceRequestDto)));
    }

    @PutMapping("/{id}")
    public ServiceResponseDto update(@PathVariable Long id, @RequestBody ServiceRequestDto serviceRequestDto) {
        Service service = serviceRequestDtoMapper.mapToModel(serviceRequestDto);
        service.setId(id);
        return serviceResponseDtoMapper.mapToDto(serviceService.save(service));
    }

    @PutMapping("/{id}/status")
    public ServiceResponseDto updateStatus(@PathVariable Long id,
                                           @RequestParam ServiceStatus status) {
        Service service = serviceService.getById(id);
        service.setStatus(status);
        return serviceResponseDtoMapper.mapToDto(serviceService.save(service));
    }
}
