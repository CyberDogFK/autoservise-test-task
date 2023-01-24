package com.mate.test.autoservice.mateautoservice.service.mapper;

import com.mate.test.autoservice.mateautoservice.dto.request.MasterRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MasterRequestDtoMapper implements RequestDtoMapper<MasterRequestDto, Master> {
    private final OrderService orderService;

    public MasterRequestDtoMapper(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Master mapToModel(MasterRequestDto dto) {
        Master master = new Master();
        master.setName(dto.getName());
        master.setSolvedOrders(dto.getSolvedOrdersIds().stream()
                .map(orderService::getById)
                .collect(Collectors.toList()));
        return master;
    }
}
