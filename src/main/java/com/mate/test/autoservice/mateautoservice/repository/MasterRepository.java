package com.mate.test.autoservice.mateautoservice.repository;

import com.mate.test.autoservice.mateautoservice.model.Master;
import jakarta.persistence.ManyToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MasterRepository extends JpaRepository<Master, Long> {
    @Override
    List<Master> findAll();
}
