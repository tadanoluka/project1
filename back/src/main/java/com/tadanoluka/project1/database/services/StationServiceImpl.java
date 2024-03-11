package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.repository.StationRepository;
import com.tadanoluka.project1.dto.PageResponse;
import com.tadanoluka.project1.dto.StationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.tadanoluka.project1.database.specifications.StationSpecs.getAllForUser;


@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;


    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    @Override
    public PageResponse<StationDTO> findAllPaged(Pageable pageRequest) {
        Page<StationDTO> page = stationRepository.findAll(pageRequest)
                .map(StationDTO::new);
        return PageResponse.of(page);
    }

    @Transactional
    @Override
    public PageResponse<StationDTO> findAllFilteredPaged(Pageable pageRequest, Specification<Station> specification) {
        Page<StationDTO> page = stationRepository
                .findAll(specification, pageRequest)
                .map(StationDTO::new);
        return PageResponse.of(page);
    }

    @Transactional
    @Override
    public List<StationDTO> findAll(Sort sort) {
        return stationRepository.findAll(sort).stream().map(StationDTO::new).toList();
    }

    @Transactional
    @Override
    public List<StationDTO> findAll(Sort sort, User user) {
        if ("admin".equals(user.getRole().getName().toLowerCase(Locale.ROOT)))
            return findAll(sort);

        return stationRepository.findAll(getAllForUser(user), sort).stream().map(StationDTO::new).toList();
    }

    @Transactional
    @Override
    public Optional<StationDTO> findStationByEsr(int esr) {
        Optional<Station> station = stationRepository.findStationByEsr(esr);
        return station.map(StationDTO::new);
    }
}
