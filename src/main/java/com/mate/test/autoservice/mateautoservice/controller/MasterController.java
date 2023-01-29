package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.MasterRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.MasterResponseDto;
import com.mate.test.autoservice.mateautoservice.dto.response.OrderResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.service.MasterService;
import com.mate.test.autoservice.mateautoservice.service.mapper.OrderResponseDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master")
public class MasterController {
    private final MasterService masterService;
    private final ResponseDtoMapper<MasterResponseDto, Master> masterResponseDtoMapper;
    private final RequestDtoMapper<MasterRequestDto, Master> masterRequestDtoMapper;
    private final OrderResponseDtoMapper orderResponseDtoMapper;

    public MasterController(MasterService masterService,
                            ResponseDtoMapper<MasterResponseDto, Master> masterResponseDtoMapper,
                            RequestDtoMapper<MasterRequestDto, Master> masterRequestDtoMapper,
                            OrderResponseDtoMapper orderResponseDtoMapper) {
        this.masterService = masterService;
        this.masterResponseDtoMapper = masterResponseDtoMapper;
        this.masterRequestDtoMapper = masterRequestDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create master and return created master with id")
    public MasterResponseDto create(@RequestBody
                                        @Parameter(description = "Information about master")
                                        MasterRequestDto requestDto) {
        return masterResponseDtoMapper.mapToDto(
                masterService.save(
                        masterRequestDtoMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(description = "Change information about master with id from path")
    public MasterResponseDto update(@PathVariable
                                    @Parameter(description =
                                            "Master id, information about who we want to change")
                                        Long id,
                                    @RequestBody
                                    @Parameter(description = "New information about master")
                                        MasterRequestDto requestDto) {
        Master master = masterRequestDtoMapper.mapToModel(requestDto);
        master.setId(id);
        return masterResponseDtoMapper.mapToDto(masterService.save(master));
    }

    @GetMapping("/{id}/orders")
    @Operation(description = "Find master orders by master id")
    public List<OrderResponseDto> getById(@PathVariable
                                              @Parameter(description = "Id of master")
                                              Long id) {
        return masterService.getById(id).getSolvedOrders().stream()
                .map(orderResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/salary")
    @Operation(description = "calculate all non paid services of master, "
            + "return payment, and change all statuses to paid")
    public BigDecimal calculateAndGiveSalary(@PathVariable
                                             @Parameter(description = "Id of master")
                                             Long id) {
        return masterService.paidForServicesForMaster(id);
    }
}
