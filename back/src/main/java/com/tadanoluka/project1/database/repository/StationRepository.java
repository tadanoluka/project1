package com.tadanoluka.project1.database.repository;

import com.tadanoluka.project1.database.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer>, JpaSpecificationExecutor<Station> {

    Optional<Station> findStationByNameIgnoreCase(String name);
    Optional<Station> findStationByEsr(int id);
}
