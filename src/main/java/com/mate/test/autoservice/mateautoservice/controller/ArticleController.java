package com.mate.test.autoservice.mateautoservice.controller;

import com.mate.test.autoservice.mateautoservice.dto.request.ArticleRequestDto;
import com.mate.test.autoservice.mateautoservice.dto.response.ArticleResponseDto;
import com.mate.test.autoservice.mateautoservice.model.Article;
import com.mate.test.autoservice.mateautoservice.service.ArticleService;
import com.mate.test.autoservice.mateautoservice.service.mapper.RequestDtoMapper;
import com.mate.test.autoservice.mateautoservice.service.mapper.ResponseDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public ArticleResponseDto get(@PathVariable Long id) {
        return articleResponseDtoMapper.mapToDto(articleService.get(id));
    }

    @GetMapping
    public List<ArticleResponseDto> getAll() {
        return articleService.getAll().stream()
                .map(articleResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Creating article and return creating object")
    public ArticleResponseDto create(@RequestBody
                                         @Parameter(description = "Get name and price")
                                         ArticleRequestDto articleRequestDto) {
        return articleResponseDtoMapper.mapToDto(
                articleService.save(
                        articleRequestDtoMapper.mapToModel(articleRequestDto)));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(description = "Change article and return changed object")
    public ArticleResponseDto update(@PathVariable
                                         @Parameter(description = "Id of object what you change")
                                         Long id,
                                     @RequestBody
                                         @Parameter(description = "New name and price")
                                         ArticleRequestDto articleRequestDto) {
        Article article = articleRequestDtoMapper.mapToModel(articleRequestDto);
        article.setId(id);
        return articleResponseDtoMapper.mapToDto(articleService.save(article));
    }
}
