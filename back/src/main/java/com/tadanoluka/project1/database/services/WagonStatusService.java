package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.WagonStatus;

public interface WagonStatusService {

    WagonStatus getOrCreateAndGetWagonStatusByName(String statusName);
}
