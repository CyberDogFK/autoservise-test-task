package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.OwnerRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.checkerframework.checker.units.qual.C;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {
    private static final String CONTROLLER_URL = "/owner";
    private static final Long TEST_ID = 1L;

    @MockBean
    private OwnerService ownerService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateOwnerWithEmptyCars() {
        Owner saved = new Owner(List.of(), List.of());
        Owner expected = new Owner(TEST_ID, List.of(), List.of());

        Mockito.when(ownerService.save(saved)).thenReturn(expected);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OwnerRequestDto(List.of(), List.of()))
                .when()
                .post(CONTROLLER_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.equalTo(TEST_ID.intValue()));
    }

    @Test
    void shouldChangeOwner() {
        Owner expected = new Owner(TEST_ID, List.of(), List.of());

        Mockito.when(ownerService.save(expected)).thenReturn(expected);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OwnerRequestDto(List.of(), List.of()))
                .when()
                .put(CONTROLLER_URL + "/{id}", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(TEST_ID.intValue()));
    }

    @Test
    void mustGetOwnerOrders() {
        List<Order> mockOrders = List.of(
                new Order(), new Order()
        );
        mockOrders.get(0).setId(1L);
        mockOrders.get(0).setCar(new Car());
        mockOrders.get(0).setServices(List.of());
        mockOrders.get(0).setArticles(List.of());
        mockOrders.get(0).setStatus(OrderStatus.COMPLETED);
        mockOrders.get(1).setId(2L);
        mockOrders.get(1).setCar(new Car());
        mockOrders.get(1).setServices(List.of());
        mockOrders.get(1).setArticles(List.of());
        mockOrders.get(1).setStatus(OrderStatus.COMPLETED);

        Mockito.when(ownerService.getOrdersOfOwner(TEST_ID)).thenReturn(mockOrders);

        RestAssuredMockMvc.
                when()
                .get(CONTROLLER_URL + "/{id}/orders", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(List.of(mockOrders.get(0).getId().intValue(),
                        mockOrders.get(1).getId().intValue())));
    }

    @Test
    void mustGetOwnerCars() {
        List<Car> mockCars = List.of(
                new Car(), new Car()
        );
        mockCars.get(0).setId(1L);
        mockCars.get(1).setId(2L);
        Owner savedOwner = new Owner(1L, mockCars, List.of());
        mockCars.get(0).setOwner(savedOwner);
        mockCars.get(1).setOwner(savedOwner);
        Mockito.when(ownerService.getById(1L)).thenReturn(savedOwner);

        RestAssuredMockMvc.when()
                .get(CONTROLLER_URL + "/{id}/cars", 1L)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(List.of(mockCars.get(0).getId().intValue(),
                        mockCars.get(1).getId().intValue())))
                .body("ownerId", Matchers.equalTo(List.of(savedOwner.getId().intValue(),
                        savedOwner.getId().intValue())));
    }
}
