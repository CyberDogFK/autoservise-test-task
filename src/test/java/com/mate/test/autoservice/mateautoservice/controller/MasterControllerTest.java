package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.MasterRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.request.OrderRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.service.MasterService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.aspectj.weaver.ast.Or;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MasterControllerTest {
    private static final String CONTROLLER_ENDPOINT = "/master";
    private static final String TEST_NAME = "Master name";
    private static final Long TEST_ID = 1L;
    private static final Long MOCK_ORDER_ID = 1L;
    private static final BigDecimal TEST_SALARY = BigDecimal.valueOf(100);

    @MockBean
    private MasterService masterService;
    @Autowired
    private MockMvc mockMvc;
    private List<Order> mockOrders;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        mockOrders = new ArrayList<>();
        mockOrder= new Order();
        mockOrder.setId(MOCK_ORDER_ID);

        mockOrders.add(mockOrder);
    }

    @Test
    void shouldCreateMaster() {
        Master savedMaster = new Master(TEST_NAME, mockOrders);
        Master expectedMaster = new Master(TEST_ID, TEST_NAME, mockOrders);

        Mockito.when(masterService.save(savedMaster)).thenReturn(expectedMaster);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new MasterRequestDto(savedMaster.getName(), List.of(MOCK_ORDER_ID)))
                .when()
                .post(CONTROLLER_ENDPOINT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.equalTo(TEST_ID.intValue()))
                .body("name", Matchers.equalTo(TEST_NAME))
                .body("solvedOrdersIds", Matchers.equalTo(List.of(MOCK_ORDER_ID.intValue())));
    }

    @Test
    void shouldChangeMaster() {
        Master savedMaster = new Master(TEST_NAME, mockOrders);
        Master expectedMaster = new Master(TEST_ID, TEST_NAME, mockOrders);

        Mockito.when(masterService.save(expectedMaster)).thenReturn(expectedMaster);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new MasterRequestDto(savedMaster.getName(), List.of(MOCK_ORDER_ID)))
                .when()
                .put(CONTROLLER_ENDPOINT + "/{id}", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(TEST_ID.intValue()))
                .body("name", Matchers.equalTo(TEST_NAME))
                .body("solvedOrdersIds", Matchers.equalTo(List.of(MOCK_ORDER_ID.intValue())));

    }

    @Test
    void shouldReturnMasterOrders() {
        Car mockCar = new Car();
        mockCar.setId(1L);
        mockOrder.setCar(mockCar);
        Service mockService = new Service();
        mockService.setId(1L);
        mockOrder.setServices(List.of(mockService));
        Article mockArticle = new Article();
        mockArticle.setId(1L);
        mockOrder.setProblemDescription("huge problem");
        mockOrder.setArticles(List.of(mockArticle));
        mockOrder.setStatus(OrderStatus.COMPLETED);
        mockOrder.setPrice(BigDecimal.valueOf(100));
        mockOrder.setAcceptanceDate(LocalDate.of(2010, 10, 10));
        mockOrder.setCompleteDate(LocalDate.of(2011, 11, 11));
        Master savedMaster = new Master(TEST_ID, TEST_NAME, mockOrders);
        Mockito.when(masterService.getById(TEST_ID)).thenReturn(savedMaster);

        RestAssuredMockMvc.when()
                .get(CONTROLLER_ENDPOINT + "/{id}" + "/orders", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(List.of(mockOrder.getId().intValue())))
                .body("carId", Matchers.equalTo(List.of(mockOrder.getCar().getId().intValue())))
                .body("problemDescription", Matchers.equalTo(List.of(mockOrder.getProblemDescription())))
                .body("acceptanceDate", Matchers.equalTo(List.of(mockOrder.getAcceptanceDate().toString())))
                .body("servicesIds", Matchers.equalTo(List.of(mockOrder.getServices().stream()
                        .map(service -> service.getId().intValue())
                        .collect(Collectors.toList()))))
                .body("articlesIds", Matchers.equalTo(List.of(mockOrder.getArticles().stream()
                        .map(article -> article.getId().intValue())
                        .collect(Collectors.toList()))))
                .body("status", Matchers.equalTo(List.of(mockOrder.getStatus().toString())))
                .body("price", Matchers.equalTo(List.of(mockOrder.getPrice().intValue())))
                .body("completedDate", Matchers.equalTo(List.of(mockOrder.getCompleteDate().toString())));
    }

    @Test
    void shouldReturnSalary() {
        Mockito.when(masterService.paidForServicesForMaster(TEST_ID)).thenReturn(TEST_SALARY);

        RestAssuredMockMvc.when()
                .get(CONTROLLER_ENDPOINT + "/{id}" + "/salary", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(Matchers.equalTo(TEST_SALARY.toString()));
    }
}
