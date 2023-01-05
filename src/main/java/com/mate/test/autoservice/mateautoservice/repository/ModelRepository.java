package com.mate.test.autoservice.mateautoservice.repository;

import com.mate.test.autoservice.mateautoservice.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
