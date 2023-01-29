package com.mate.test.autoservice.mateautoservice.service.impl;

import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import com.mate.test.autoservice.mateautoservice.repository.MasterRepository;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MasterServiceImplTest {
    private static final OrderStatus COMPLETED_ORDER_STATUS = OrderStatus.COMPLETED;
    private static final OrderStatus PAID_ORDER_STATUS = OrderStatus.PAID_FOR;
    private static final ServiceStatus PAID_SERVICE_STATUS = ServiceStatus.PAID;
    private static final ServiceStatus NON_PAID_SERVICE_STATUS = ServiceStatus.NON_PAID;
    private static Order completeOrder;
    private static  Order paidOrder;
    private static Master master;

    private static Service notPaidServicePrice100;
    private static Service notPaidServicePrice200;

    @InjectMocks
    private MasterServiceImpl masterService;

    @Mock
    private ServiceService serviceService;
    @Mock
    private OrderService orderService;
    @Mock
    private MasterRepository masterRepository;

    @BeforeAll
    static void beforeAll() {
        master = new Master();
        master.setId(1L);
    }

    @BeforeEach
    void beforeEach() {
        completeOrder = new Order();
        completeOrder.setStatus(COMPLETED_ORDER_STATUS);
        paidOrder = new Order();
        paidOrder.setStatus(PAID_ORDER_STATUS);

        Service paidService = new Service();
        paidService.setPrice(BigDecimal.valueOf(1));
        paidService.setStatus(PAID_SERVICE_STATUS);
        paidService.setMaster(master);
        paidOrder.setServices(List.of(paidService));

        notPaidServicePrice100 = new Service();
        notPaidServicePrice100.setStatus(NON_PAID_SERVICE_STATUS);
        notPaidServicePrice100.setPrice(BigDecimal.valueOf(100));
        notPaidServicePrice100.setMaster(master);
        notPaidServicePrice200 = new Service();
        notPaidServicePrice200.setStatus(NON_PAID_SERVICE_STATUS);
        notPaidServicePrice200.setPrice(BigDecimal.valueOf(200));
        notPaidServicePrice200.setMaster(master);
        completeOrder.setServices(List.of(notPaidServicePrice100, notPaidServicePrice200));

        master.setSolvedOrders(List.of(completeOrder, paidOrder));
    }

    @Test
    void getNotPaidOrdersForMasterTest_Ok() {
        Mockito.when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));
        List<Order> notPaidOrdersForMaster = masterService.getNotPaidOrdersForMaster(master.getId());
        Assertions.assertEquals(1, notPaidOrdersForMaster.size(),
                "You must contain only not paid, completed orders");
        Assertions.assertEquals(COMPLETED_ORDER_STATUS, notPaidOrdersForMaster.get(0).getStatus(),
                "You must return only completed order");
    }

    @Test
    void getNotPaidServicesForMasterTest_Ok() {
        Mockito.when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));
        List<Service> notPaidServicesForMaster = masterService.getNotPaidServicesForMaster(master.getId());
        Assertions.assertEquals(2, notPaidServicesForMaster.size());
        Assertions.assertEquals(NON_PAID_SERVICE_STATUS, notPaidServicesForMaster.get(0).getStatus());
    }

    @Test
    void paidForServicesForMasterTest_Ok() {
        Mockito.when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));
        Mockito.when(serviceService.saveAll(List.of(notPaidServicePrice100, notPaidServicePrice200))).thenReturn(List.of());
        Mockito.when(orderService.saveAll(List.of(completeOrder))).thenReturn(List.of());

        BigDecimal actualPaid = masterService.paidForServicesForMaster(master.getId());
        Assertions.assertEquals(BigDecimal.valueOf(180.0), actualPaid);
    }
}
