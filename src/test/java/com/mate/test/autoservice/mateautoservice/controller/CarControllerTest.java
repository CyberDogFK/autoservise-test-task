package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.CarRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.service.CarService;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {
    private static final String CONTROLLER_URL = "/car";
    private static final Long TEST_ID = 1L;
    private static final Integer TEST_ID_EXPECTED = 1;
    private static final String TEST_BRAND = "test brand";
    private static final String TEST_MODEL = "test model";
    private static final Integer TEST_YEAR = 2000;
    private static final String TEST_REG_NUMBER = "AA0000BB";
    private static final Long TEST_OWNER_ID = 1L;
    private static final Integer TEST_OWNER_ID_EXPECTED = 1;

    @MockBean
    private CarService carService;
    @MockBean
    private OwnerService ownerService;
    private Owner testOwner;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        testOwner = new Owner();
        testOwner.setId(1L);
        testOwner.setOrders(List.of());
        testOwner.setCars(new ArrayList<>());
    }

    @Test
    void shouldCreateCar() {
        Car savedCar = new Car(TEST_BRAND, TEST_MODEL, TEST_YEAR, TEST_REG_NUMBER, testOwner);
        Car expectedCar = new Car(TEST_ID, TEST_BRAND, TEST_MODEL, TEST_YEAR, TEST_REG_NUMBER, testOwner);
        Mockito.when(carService.save(savedCar)).thenReturn(expectedCar);
        Mockito.when(ownerService.getById(TEST_OWNER_ID)).thenReturn(testOwner);
        Mockito.when(ownerService.save(testOwner)).thenReturn(testOwner);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarRequestDto(savedCar.getBrand(), savedCar.getModel(), savedCar.getYear(),
                        savedCar.getRegNumber(), savedCar.getOwner().getId()))
                .when()
                .post(CONTROLLER_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.equalTo(TEST_ID_EXPECTED))
                .body("brand", Matchers.equalTo(TEST_BRAND))
                .body("model", Matchers.equalTo(TEST_MODEL))
                .body("year", Matchers.equalTo(TEST_YEAR))
                .body("regNumber", Matchers.equalTo(TEST_REG_NUMBER))
                .body("ownerId", Matchers.equalTo(TEST_OWNER_ID_EXPECTED));
    }

    @Test
    void shouldChangeMaster() {
        Car savedCar = new Car(TEST_BRAND, TEST_MODEL, TEST_YEAR, TEST_REG_NUMBER, testOwner);
        Car expectedCar = new Car(TEST_ID, TEST_BRAND, TEST_MODEL, TEST_YEAR, TEST_REG_NUMBER, testOwner);

        Mockito.when(carService.save(expectedCar)).thenReturn(expectedCar);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarRequestDto(savedCar.getBrand(), savedCar.getModel(), savedCar.getYear(),
                        savedCar.getRegNumber(), savedCar.getOwner().getId()))
                .when()
                .put(CONTROLLER_URL + "/{id}", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(TEST_ID_EXPECTED))
                .body("brand", Matchers.equalTo(TEST_BRAND))
                .body("model", Matchers.equalTo(TEST_MODEL))
                .body("year", Matchers.equalTo(TEST_YEAR))
                .body("regNumber", Matchers.equalTo(TEST_REG_NUMBER))
                .body("ownerId", Matchers.equalTo(TEST_OWNER_ID_EXPECTED));
    }
}
