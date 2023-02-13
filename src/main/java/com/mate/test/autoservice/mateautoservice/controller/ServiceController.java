package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ServiceRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.ServiceResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<ServiceResponseDto> getAll() {
        return serviceService.getAll().stream()
                .map(serviceResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ServiceResponseDto getById(@PathVariable Long id) {
        return serviceResponseDtoMapper.mapToDto(serviceService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Creat new service and return creating object")
    public ServiceResponseDto create(@RequestBody
                                         @Parameter(description =
                                                 "Take name, masterId, price, serviceStatus")
                                         ServiceRequestDto serviceRequestDto) {
        return serviceResponseDtoMapper.mapToDto(
                serviceService.save(
                        serviceRequestDtoMapper.mapToModel(serviceRequestDto)));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(description = "Change specific service")
    public ServiceResponseDto update(@PathVariable
                                         @Parameter(description = "Service id")
                                         Long id,
                                     @RequestBody
                                         @Parameter(description =
                                                 "Take object with new information, same in post")
                                         ServiceRequestDto serviceRequestDto) {
        Service service = serviceRequestDtoMapper.mapToModel(serviceRequestDto);
        service.setId(id);
        return serviceResponseDtoMapper.mapToDto(serviceService.save(service));
    }

    @CrossOrigin
    @PutMapping("/{id}/status")
    @Operation(description = "Change status of service")
    public ServiceResponseDto updateStatus(@PathVariable
                                               @Parameter(description = "Service id")
                                               Long id,
                                           @RequestParam
                                               @Parameter(description =
                                                       "Take status (PAID, NON_PAID)")
                                               ServiceStatus status) {
        System.out.println("Status: " + status);
        Service service = serviceService.getById(id);
        service.setStatus(status);
        return serviceResponseDtoMapper.mapToDto(serviceService.save(service));
    }
}
