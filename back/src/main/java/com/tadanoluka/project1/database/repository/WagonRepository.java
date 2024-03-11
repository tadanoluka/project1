package com.tadanoluka.project1.database.repository;

import com.tadanoluka.project1.database.entity.Wagon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WagonRepository extends JpaRepository<Wagon, Long>, JpaSpecificationExecutor<Wagon> {

    Optional<Wagon> findWagonById(long id);
}
