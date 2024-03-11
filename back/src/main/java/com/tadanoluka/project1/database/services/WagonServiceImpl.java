package com.tadanoluka.project1.database.services;


import static com.tadanoluka.project1.database.specifications.WagonSpecs.getAllForUser;

import com.tadanoluka.project1.database.entity.*;
import com.tadanoluka.project1.database.repository.*;
import com.tadanoluka.project1.dto.PageResponse;
import com.tadanoluka.project1.dto.WagonDTO;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WagonServiceImpl implements WagonService {

    private final WagonRepository wagonRepository;
    private final StationRepository stationRepository;
    private final FreightRepository freightRepository;
    private final ConsigneeRepository consigneeRepository;
    private final WagonStatusService wagonStatusService;
    private final UserRepository userRepository;

    @Autowired
    public WagonServiceImpl(WagonRepository wagonRepository,
                            StationRepository stationRepository,
                            FreightRepository freightRepository,
                            ConsigneeRepository consigneeRepository,
                            WagonStatusService wagonStatusService,
                            UserRepository userRepository) {
        this.wagonRepository = wagonRepository;
        this.stationRepository = stationRepository;
        this.freightRepository = freightRepository;
        this.consigneeRepository = consigneeRepository;
        this.wagonStatusService = wagonStatusService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public PageResponse<WagonDTO> findAllPaged(Pageable pageRequest) throws PropertyReferenceException {
        return PageResponse.of(wagonRepository.findAll(pageRequest)
                .map(WagonDTO::new));
    }

    @Transactional
    @Override
    public PageResponse<WagonDTO> findAllFilteredPaged(Pageable pageRequest,
                                                       Specification<Wagon> specification) throws PropertyReferenceException {
        Page<WagonDTO> page = wagonRepository
                .findAll(specification, pageRequest)
                .map(WagonDTO::new);
        return PageResponse.of(page);
    }

    @Transactional
    @Override
    public PageResponse<WagonDTO> findAllForUserPaged(Pageable pageRequest, User user) throws PropertyReferenceException {
        if ("admin".equals(user.getRole().getName().toLowerCase(Locale.ROOT)))
            return findAllPaged(pageRequest);
        return PageResponse.of(wagonRepository.findAll(getAllForUser(user), pageRequest)
                .map(WagonDTO::new));
    }

    @Transactional
    @Override
    public PageResponse<WagonDTO> findAllForUserFilteredPaged(Pageable pageRequest,
                                                              Specification<Wagon> specification,
                                                              User user) throws PropertyReferenceException {
        if ("admin".equals(user.getRole().getName().toLowerCase(Locale.ROOT)))
            return findAllFilteredPaged(pageRequest, specification);

        Page<WagonDTO> page = wagonRepository
                .findAll(getAllForUser(user).and(specification),pageRequest)
                .map(WagonDTO::new);
        return PageResponse.of(page);
    }

    @Transactional
    @Override
    public void loadWagons(List<Wagon> wagonList) {
        wagonRepository.saveAll(wagonList);
    }

    @Transactional
    @Override
    public Optional<WagonDTO> findWagonById(long id) {
        return wagonRepository.findWagonById(id).map(WagonDTO::new);
    }
}
