package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ArticleRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.ArticleResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.mapper.ArticleRequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ArticleResponseDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {
    private static final String CONTROLLER_URL = "/article";
    private static final String TEST_ARTICLE_NAME = "Test article";

    @MockBean
    private ArticleService articleService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateArticle() {
        Article articleToSave = new Article(TEST_ARTICLE_NAME, BigDecimal.valueOf(100));
        Article expectedArticle = new Article(1L, TEST_ARTICLE_NAME, BigDecimal.valueOf(100));
        Mockito.when(articleService.save(articleToSave))
                .thenReturn(expectedArticle);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ArticleRequestDto(articleToSave.getName(), articleToSave.getPrice()))
                .when()
                .post(CONTROLLER_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo(TEST_ARTICLE_NAME))
                .body("price", Matchers.equalTo(100));
    }

    @Test
    void shouldChangeMaster() {
        Article articleToSave = new Article(TEST_ARTICLE_NAME, BigDecimal.valueOf(100));
        Long id = 1L;
        Article expectedArticle = new Article(1L, TEST_ARTICLE_NAME, BigDecimal.valueOf(100));

        Mockito.when(articleService.save(expectedArticle))
                .thenReturn(expectedArticle);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ArticleRequestDto(articleToSave.getName(), articleToSave.getPrice()))
                .when()
                .put(CONTROLLER_URL + "/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo(TEST_ARTICLE_NAME))
                .body("price", Matchers.equalTo(100));

    }
}
