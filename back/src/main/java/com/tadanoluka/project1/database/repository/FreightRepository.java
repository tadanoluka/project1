package com.tadanoluka.project1.database.repository;

import com.tadanoluka.project1.database.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreightRepository extends JpaRepository<Freight, Integer>, JpaSpecificationExecutor<Freight> {

    Optional<Freight> findFreightById(int id);
    Optional<Freight> findFreightByEtsng(int etsng);
}
