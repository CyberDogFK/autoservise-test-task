package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.OrderRequestDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.model.Car;
import com.mate.test.autoservice.mateautoservice.model.Master;
import com.mate.test.autoservice.mateautoservice.model.Order;
import com.mate.test.autoservice.mateautoservice.model.OrderStatus;
import com.mate.test.autoservice.mateautoservice.model.Owner;
import com.mate.test.autoservice.mateautoservice.model.Service;
import com.mate.test.autoservice.mateautoservice.model.ServiceStatus;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.CarService;
import com.mate.test.autoservice.mateautoservice.service.MasterService;
import com.mate.test.autoservice.mateautoservice.service.OrderService;
import com.mate.test.autoservice.mateautoservice.service.OwnerService;
import com.mate.test.autoservice.mateautoservice.service.ServiceService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
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
public class OrderControllerTest {
    private static final String CONTROLLER_URL = "/order";
    private static final Long TEST_ID = 1L;
    private static final String TEST_PROBLEM_DESCRIPTION = "Huge problem!";
    private static final LocalDate TEST_ACCEPTANCE_DATE = LocalDate.of(2010, 10, 10);
    private static final LocalDate TEST_COMPLETED_DATE = LocalDate.of(2011, 11, 11);
    private static final OrderStatus TEST_ACCEPTED_STATUS = OrderStatus.ACCEPTED;
    private static final Long ownerId = 1L;


    private Car mockCar;
    private Owner mockOwner;
    private Service mockService;
    private Article mockArticle;
    @MockBean
    private CarService carService;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private MasterService masterService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ArticleService articleService;
    @MockBean
    private ServiceService serviceService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        mockOwner = new Owner(TEST_ID, List.of(), new ArrayList<>());
        mockCar = new Car(TEST_ID, "brand", "model", 2000, "regNumber", mockOwner);
        mockService = new Service(TEST_ID, "service", new Master(), BigDecimal.valueOf(100), ServiceStatus.NON_PAID);
        mockArticle = new Article(TEST_ID, "article", BigDecimal.valueOf(100));
    }

    @Test
    void shouldCreateOrder() {
        Order savedOrder = new Order(mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), TEST_ACCEPTED_STATUS, TEST_COMPLETED_DATE);
        Order savedOrderWithPrice = new Order(mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), TEST_ACCEPTED_STATUS, mockService.getPrice(), TEST_COMPLETED_DATE);
        Order expectedOrder = new Order(TEST_ID, mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), TEST_ACCEPTED_STATUS, mockService.getPrice(), TEST_COMPLETED_DATE);

        Mockito.when(carService.getById(TEST_ID)).thenReturn(mockCar);
        Mockito.when(orderService.getPriceWithDiscount(savedOrder)).thenReturn(expectedOrder.getPrice());
        Mockito.when(ownerService.save(mockOwner)).thenReturn(mockOwner);
        Mockito.when(orderService.save(savedOrderWithPrice)).thenReturn(expectedOrder);

        List<Long> serviceIds = savedOrder.getServices().stream()
                .map(Service::getId)
                .toList();
        List<Long> articleIds = savedOrder.getArticles().stream()
                .map(Article::getId)
                .toList();
        Mockito.when(serviceService.getAllByIds(serviceIds)).thenReturn(expectedOrder.getServices());
        Mockito.when(articleService.getAllByIds(articleIds)).thenReturn(expectedOrder.getArticles());

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(savedOrder.getCar().getId(),
                        savedOrder.getProblemDescription(), savedOrder.getAcceptanceDate(),
                        serviceIds, articleIds,
                        savedOrder.getStatus(), savedOrder.getCompleteDate()))
                .when()
                .post(CONTROLLER_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.equalTo(TEST_ID.intValue()))
                .body("carId", Matchers.equalTo(TEST_ID.intValue()))
                .body("problemDescription", Matchers.equalTo(TEST_PROBLEM_DESCRIPTION))
                .body("acceptanceDate", Matchers.equalTo(TEST_ACCEPTANCE_DATE.toString()))
                .body("servicesIds", Matchers.equalTo(List.of(TEST_ID.intValue())))
                .body("articlesIds", Matchers.equalTo(List.of(TEST_ID.intValue())))
                .body("status", Matchers.equalTo(TEST_ACCEPTED_STATUS.name()))
                .body("price", Matchers.equalTo(mockService.getPrice().intValue()))
                .body("completedDate", Matchers.equalTo(TEST_COMPLETED_DATE.toString()));
    }

    @Test
    void shouldAddNewArticlesToOrder() {
        String secondMockArticleName = "second name";
        String thirdMockArticleName = "third name";
        BigDecimal secondMockArticlePrice = BigDecimal.valueOf(200);
        BigDecimal thirdMockArticlePrice = BigDecimal.valueOf(300);
        List<Article> mockArticles = List.of(new Article(2L, secondMockArticleName, secondMockArticlePrice),
                new Article(3L, thirdMockArticleName, thirdMockArticlePrice));
        List<Long> mockArticlesIds = mockArticles.stream()
                .map(Article::getId)
                .toList();
        Order savedOrder = new Order(TEST_ID, mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                new ArrayList<>(List.of(mockArticle)), TEST_ACCEPTED_STATUS, mockService.getPrice(), TEST_COMPLETED_DATE);
        List<Article> expectedArticles = List.of(mockArticle, mockArticles.get(0), mockArticles.get(1));
        Order expectedOrder =  new Order(TEST_ID, mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                expectedArticles,
                        TEST_ACCEPTED_STATUS, mockService.getPrice(), TEST_COMPLETED_DATE);

        Mockito.when(orderService.getById(TEST_ID)).thenReturn(savedOrder);
        Mockito.when(articleService.getAllByIds(mockArticlesIds)).thenReturn(mockArticles);
        Mockito.when(orderService.addArticleForOrder(mockArticles, savedOrder)).thenReturn(expectedOrder);
        Mockito.when(orderService.save(expectedOrder)).thenReturn(expectedOrder);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(mockArticlesIds)
                .when()
                .post(CONTROLLER_URL + "/{id}" + "/article", TEST_ID)
                .then()
                .body("articlesIds", Matchers.equalTo(expectedArticles.stream()
                        .map(a -> a.getId().intValue())
                        .collect(Collectors.toList())));
    }

    @Test
    void shouldSaveOrderWithSpecifiedId() {
        Order savedOrder = new Order(mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), TEST_ACCEPTED_STATUS, TEST_COMPLETED_DATE);
        Order savedOrderWithPrice = new Order(TEST_ID, mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), TEST_ACCEPTED_STATUS, mockService.getPrice(), TEST_COMPLETED_DATE);
        Order expectedOrder = new Order(TEST_ID, mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), TEST_ACCEPTED_STATUS, mockService.getPrice(), TEST_COMPLETED_DATE);
        savedOrder.setId(1L);
        Mockito.when(carService.getById(TEST_ID)).thenReturn(mockCar);
        Mockito.when(orderService.getPriceWithDiscount(savedOrder)).thenReturn(expectedOrder.getPrice());
        Mockito.when(ownerService.save(mockOwner)).thenReturn(mockOwner);
        Mockito.when(orderService.save(expectedOrder)).thenReturn(expectedOrder);

        List<Long> serviceIds = savedOrder.getServices().stream()
                .map(Service::getId)
                .toList();
        List<Long> articleIds = savedOrder.getArticles().stream()
                .map(Article::getId)
                .toList();
        Mockito.when(serviceService.getAllByIds(serviceIds)).thenReturn(expectedOrder.getServices());
        Mockito.when(articleService.getAllByIds(articleIds)).thenReturn(expectedOrder.getArticles());

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(savedOrder.getCar().getId(),
                        savedOrder.getProblemDescription(), savedOrder.getAcceptanceDate(),
                        serviceIds, articleIds,
                        savedOrder.getStatus(), savedOrder.getCompleteDate()))
                .when()
                .put(CONTROLLER_URL + "/{id}", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(TEST_ID.intValue()))
                .body("carId", Matchers.equalTo(TEST_ID.intValue()))
                .body("problemDescription", Matchers.equalTo(TEST_PROBLEM_DESCRIPTION))
                .body("acceptanceDate", Matchers.equalTo(TEST_ACCEPTANCE_DATE.toString()))
                .body("servicesIds", Matchers.equalTo(List.of(TEST_ID.intValue())))
                .body("articlesIds", Matchers.equalTo(List.of(TEST_ID.intValue())))
                .body("status", Matchers.equalTo(TEST_ACCEPTED_STATUS.name()))
                .body("price", Matchers.equalTo(mockService.getPrice().intValue()))
                .body("completedDate", Matchers.equalTo(TEST_COMPLETED_DATE.toString()));
    }

    @Test
    void shouldChangeOrderStatus() {
        Order savedOrder = new Order(mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), OrderStatus.ACCEPTED, TEST_COMPLETED_DATE);
        Order expectedOrder = new Order(TEST_ID, mockCar, TEST_PROBLEM_DESCRIPTION,
                TEST_ACCEPTANCE_DATE, List.of(mockService),
                List.of(mockArticle), OrderStatus.COMPLETED, mockService.getPrice(), TEST_COMPLETED_DATE);
        Master master = new Master();
        master.setSolvedOrders(new ArrayList<>());
        Service service = new Service(TEST_ID, "name", master, BigDecimal.valueOf(100), ServiceStatus.NON_PAID);
        List<Service> services = new ArrayList<>();
        services.add(service);
        savedOrder.setServices(services);
        Master expectedMaster = new Master();
        expectedMaster.setSolvedOrders(List.of(expectedOrder));

        Mockito.when(orderService.getById(1L)).thenReturn(savedOrder);
        Mockito.when(masterService.save(master)).thenReturn(expectedMaster);
        Mockito.when(orderService.save(savedOrder)).thenReturn(expectedOrder);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(TEST_ID, "", TEST_ACCEPTANCE_DATE,
                        List.of(service.getId()), List.of(), TEST_ACCEPTED_STATUS, TEST_COMPLETED_DATE))
                .param("orderStatus", OrderStatus.COMPLETED)
                .when()
                .put(CONTROLLER_URL + "/{id}/status", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(1))
                .body("status", Matchers.equalTo(OrderStatus.COMPLETED.name()));
    }

    @Test
    void shouldReturnPrice() {
        Order expectedOrder = new Order();
        expectedOrder.setPrice(BigDecimal.valueOf(100));

        Mockito.when(orderService.getById(TEST_ID)).thenReturn(expectedOrder);

        RestAssuredMockMvc.when()
                .get(CONTROLLER_URL + "/{id}/price", TEST_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(Matchers.equalTo(expectedOrder.getPrice().toString()));
    }
}
