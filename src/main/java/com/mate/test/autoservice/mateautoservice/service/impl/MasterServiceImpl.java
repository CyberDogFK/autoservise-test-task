package com.mate.test.autoservice.mateautoservice.service.impl;

import static com.mate.test.autoservice.mateautoservice.model.ServiceStatus.NON_PAID;
import static com.mate.test.autoservice.mateautoservice.model.ServiceStatus.PAID;

import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.repository.MasterRepository;
import com.mate.test.autoservice.mateautoservice.service.MasterService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MasterServiceImpl implements MasterService {
    private static final double PAYMENT = 40; //in percent
    private final MasterRepository masterRepository;
    private final ServiceService serviceService;
    private final OrderService orderService;

    public MasterServiceImpl(MasterRepository masterRepository,
                             ServiceService serviceService,
                             OrderService orderService) {
        this.masterRepository = masterRepository;
        this.serviceService = serviceService;
        this.orderService = orderService;
    }

    public List<Order> getNotPaidOrdersForMaster(Long id) {
        Master master = getById(id);
        return master.getSolvedOrders().stream()
                .filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))
                .collect(Collectors.toList());
    }

    public List<Service> getNotPaidServicesForMaster(Long id) {
        Master master = getById(id);
        return master.getSolvedOrders().stream()
                .filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))
                .flatMap(o -> o.getServices().stream())
                .filter(s -> s.getMaster().equals(master)
                        && s.getStatus().equals(NON_PAID))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Double paidForServicesForMaster(Long id) {
        List<Service> notPaidServices = getNotPaidServicesForMaster(id);
        double result = notPaidServices.stream()
                .peek(s -> s.setStatus(PAID))
                .mapToDouble(s -> s.getPrice().doubleValue())
                .sum();
        result -= PAYMENT * result / 100;
        serviceService.saveAll(notPaidServices);
        List<Order> notPaidOrders = getNotPaidOrdersForMaster(id);
        for (Order order : notPaidOrders) {
            List<Service> services = order.getServices();
            long count = services.stream()
                    .map(Service::getStatus)
                    .filter(s -> s.equals(PAID))
                    .count();
            if (count == services.size()) {
                order.setStatus(OrderStatus.PAID_FOR);
            }
        }
        orderService.saveAll(notPaidOrders);
        return result;
    }

    @Override
    public Master save(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master getById(Long id) {
        return masterRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find master with id " + id));
    }
}
