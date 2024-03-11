package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.services.types.pageable.PageableService;
import com.tadanoluka.project1.dto.StationDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface StationService extends PageableService<Station, StationDTO> {
    List<StationDTO> findAll(Sort sort);
    List<StationDTO> findAll(Sort sort, User user);
    Optional<StationDTO> findStationByEsr(int esr);

}

