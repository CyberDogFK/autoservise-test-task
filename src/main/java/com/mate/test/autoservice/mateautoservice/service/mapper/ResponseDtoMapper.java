package com.mate.test.autoservice.mateautoservice.service.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T model);
}
