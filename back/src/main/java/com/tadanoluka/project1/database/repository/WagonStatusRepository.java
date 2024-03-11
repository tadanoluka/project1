package com.tadanoluka.project1.database.repository;

import com.tadanoluka.project1.database.entity.WagonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WagonStatusRepository extends JpaRepository<WagonStatus, Integer> {

    Optional<WagonStatus> findWagonStatusByNameIgnoreCase(String name);
}
