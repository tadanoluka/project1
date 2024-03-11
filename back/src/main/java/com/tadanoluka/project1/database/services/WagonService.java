package com.tadanoluka.project1.database.services;


import com.tadanoluka.project1.database.entity.Wagon;
import com.tadanoluka.project1.database.services.types.pageable.PageableForUserService;
import com.tadanoluka.project1.dto.StationDTO;
import com.tadanoluka.project1.dto.WagonDTO;
import java.util.List;
import java.util.Optional;


public interface WagonService extends PageableForUserService<Wagon, WagonDTO> {
    void loadWagons(List<Wagon> wagonList);

    Optional<WagonDTO> findWagonById(long id);
}
