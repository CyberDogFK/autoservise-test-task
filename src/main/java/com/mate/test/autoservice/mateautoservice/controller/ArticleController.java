package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ArticleRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.ArticleResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final RequestDtoMapper<ArticleRequestDto, Article> articleRequestDtoMapper;
    private final ResponseDtoMapper<ArticleResponseDto, Article> articleResponseDtoMapper;

    public ArticleController(ArticleService articleService,
                             RequestDtoMapper<ArticleRequestDto, Article> articleRequestDtoMapper,
                             ResponseDtoMapper<ArticleResponseDto,
                                     Article> articleResponseDtoMapper) {
        this.articleService = articleService;
        this.articleRequestDtoMapper = articleRequestDtoMapper;
        this.articleResponseDtoMapper = articleResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation("Creating article and return creating object")
    public ArticleResponseDto create(@RequestBody
                                         @ApiParam("Get name and price")
                                         ArticleRequestDto articleRequestDto) {
        return articleResponseDtoMapper.mapToDto(
                articleService.save(
                        articleRequestDtoMapper.mapToModel(articleRequestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation("Change article and return changed object")
    public ArticleResponseDto update(@PathVariable
                                         @ApiParam("Id of object what you change")
                                         Long id,
                                     @RequestBody
                                         @ApiParam("New name and price")
                                         ArticleRequestDto articleRequestDto) {
        Article article = articleRequestDtoMapper.mapToModel(articleRequestDto);
        article.setId(id);
        return articleResponseDtoMapper.mapToDto(articleService.save(article));
    }
}
