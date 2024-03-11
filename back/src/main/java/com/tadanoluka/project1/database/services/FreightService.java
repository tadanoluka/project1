package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.Freight;
import com.tadanoluka.project1.database.services.types.pageable.PageableService;
import com.tadanoluka.project1.dto.FreightDTO;
import com.tadanoluka.project1.dto.StationDTO;

import java.util.Optional;

public interface FreightService extends PageableService<Freight, FreightDTO> {
    Optional<FreightDTO> findFreightByEtsng(int etsng);
}
