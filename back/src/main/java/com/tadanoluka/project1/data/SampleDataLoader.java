package com.tadanoluka.project1.data;

import com.tadanoluka.project1.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);

    private final FreightRepository freightRepository;
    private final ConsigneeRepository consigneeRepository;
    private final StationRepository stationRepository;
    private final WagonRepository wagonRepository;
    private final WagonStatusRepository wagonStatusRepository;

    public SampleDataLoader(
        @Autowired FreightRepository freightRepository,
        @Autowired ConsigneeRepository consigneeRepository,
        @Autowired StationRepository stationRepository,
        @Autowired WagonRepository wagonRepository,
        @Autowired WagonStatusRepository wagonStatusRepository
    ) {
        this.freightRepository = freightRepository;
        this.consigneeRepository = consigneeRepository;
        this.stationRepository = stationRepository;
        this.wagonRepository = wagonRepository;
        this.wagonStatusRepository = wagonStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
