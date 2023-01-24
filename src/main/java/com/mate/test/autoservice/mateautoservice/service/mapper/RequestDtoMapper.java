package com.mate.test.autoservice.mateautoservice.service.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
