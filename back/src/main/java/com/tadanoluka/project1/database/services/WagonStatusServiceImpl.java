package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.WagonStatus;
import com.tadanoluka.project1.database.repository.WagonStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WagonStatusServiceImpl implements WagonStatusService{

    WagonStatusRepository wagonStatusRepository;

    @Autowired
    public void setWagonStatusRepository(WagonStatusRepository wagonStatusRepository) {
        this.wagonStatusRepository = wagonStatusRepository;
    }

    @Transactional
    @Override
    public WagonStatus getOrCreateAndGetWagonStatusByName(String statusName) {
        Optional<WagonStatus> wagonStatus = wagonStatusRepository.findWagonStatusByNameIgnoreCase(statusName);
        return wagonStatus.orElseGet(() -> wagonStatusRepository.save(new WagonStatus(statusName)));
    }
}
