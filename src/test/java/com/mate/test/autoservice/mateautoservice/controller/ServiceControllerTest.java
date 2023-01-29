package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ServiceRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceControllerTest {
    private static final String CONTROLLER_URL = "/service";
    @MockBean
    private ServiceService serviceService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateService() {
        Service savedService
                = new Service("name", new Master(), BigDecimal.valueOf(100), ServiceStatus.NON_PAID);
        Service expectedService =
                new Service(1L, "name", new Master(), BigDecimal.valueOf(100), ServiceStatus.NON_PAID);

        Mockito.when(serviceService.save(savedService)).thenReturn(expectedService);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ServiceRequestDto(savedService.getName(), 1L,
                        savedService.getPrice(), savedService.getStatus()))
                .when()
                .post(CONTROLLER_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.equalTo(expectedService.getId().intValue()))
                .body("name", Matchers.equalTo(expectedService.getName()))
                .body("price", Matchers.equalTo(expectedService.getPrice().intValue()))
                .body("status", Matchers.equalTo(expectedService.getStatus().name()));
    }

    @Test
    void shouldChangeService() {
        Service savedService
                = new Service("name", new Master(), BigDecimal.valueOf(100), ServiceStatus.NON_PAID);
        Service expectedService =
                new Service(1L, "name", new Master(), BigDecimal.valueOf(100), ServiceStatus.NON_PAID);

        Mockito.when(serviceService.save(expectedService)).thenReturn(expectedService);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ServiceRequestDto(savedService.getName(), 1L,
                        savedService.getPrice(), savedService.getStatus()))
                .when()
                .put(CONTROLLER_URL + "/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(expectedService.getId().intValue()))
                .body("name", Matchers.equalTo(expectedService.getName()))
                .body("price", Matchers.equalTo(expectedService.getPrice().intValue()))
                .body("status", Matchers.equalTo(expectedService.getStatus().name()));
    }

    @Test
    void shouldChangeServiceStatus() {
        Master master = new Master();
        Service service = new Service();
        service.setId(1L);
        service.setStatus(ServiceStatus.NON_PAID);
        service.setMaster(master);

        Service expectedService = new Service();
        expectedService.setId(1L);
        expectedService.setStatus(ServiceStatus.PAID);
        expectedService.setMaster(master);

        Mockito.when(serviceService.getById(1L)).thenReturn(service);
        Mockito.when(serviceService.save(expectedService)).thenReturn(expectedService);

        RestAssuredMockMvc.given()
                .param("status", ServiceStatus.PAID.name())
                .when()
                .put(CONTROLLER_URL + "/{id}/status", 1L)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("status", Matchers.equalTo(ServiceStatus.PAID.name()));
    }
}
