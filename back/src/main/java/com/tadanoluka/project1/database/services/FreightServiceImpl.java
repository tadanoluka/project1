package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.Freight;
import com.tadanoluka.project1.database.repository.FreightRepository;
import com.tadanoluka.project1.dto.FreightDTO;
import com.tadanoluka.project1.dto.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class FreightServiceImpl implements FreightService {

    private final FreightRepository freightRepository;

    @Autowired
    public FreightServiceImpl(FreightRepository freightRepository) {
        this.freightRepository = freightRepository;
    }

    @Transactional
    @Override
    public PageResponse<FreightDTO> findAllPaged(Pageable pageRequest) {
        Page<FreightDTO> page = freightRepository.findAll(pageRequest)
                .map(FreightDTO::new);
        return PageResponse.of(page);
    }

    @Transactional
    @Override
    public PageResponse<FreightDTO> findAllFilteredPaged(Pageable pageRequest, Specification<Freight> specification) {
        Page<FreightDTO> page = freightRepository
                .findAll(specification, pageRequest)
                .map(FreightDTO::new);
        return PageResponse.of(page);
    }

    @Transactional
    @Override
    public Optional<FreightDTO> findFreightByEtsng(int etsng) {
        return freightRepository.findFreightByEtsng(etsng).map(FreightDTO::new);
    }
}
