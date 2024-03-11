package com.tadanoluka.project1.storage.parsers;

import com.tadanoluka.project1.database.entity.*;
import com.tadanoluka.project1.database.repository.ConsigneeRepository;
import com.tadanoluka.project1.database.repository.FreightRepository;
import com.tadanoluka.project1.database.repository.StationRepository;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.database.services.WagonStatusService;
import com.tadanoluka.project1.storage.StorageProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DummyWaggonParser {

    private final Path rootLocation;
    private final StationRepository stationRepository;
    private final WagonStatusService wagonStatusService;
    private final UserRepository userRepository;
    private final FreightRepository freightRepository;
    private final ConsigneeRepository consigneeRepository;
    private List<WagonParser> parsers;

    @Autowired
    public void setParsers(List<WagonParser> parsers) {
        this.parsers = parsers;
    }

    @Autowired
    public DummyWaggonParser(StorageProperties properties,
                             StationRepository stationRepository,
                             WagonStatusService wagonStatusService,
                             UserRepository userRepository,
                             FreightRepository freightRepository,
                             ConsigneeRepository consigneeRepository) {
        this.rootLocation = properties.getWagonsFileLocation();
        this.stationRepository = stationRepository;
        this.wagonStatusService = wagonStatusService;
        this.userRepository = userRepository;
        this.freightRepository = freightRepository;
        this.consigneeRepository = consigneeRepository;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        if(this.parsers == null) {
            throw new WagonParserException("Wagon parsers not initialized");
        }
    }

    public List<Wagon> parse(String filename, int operationStationEsr, User user) {
        Optional<String> fileExtension = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));

        if (fileExtension.isEmpty())
            throw new WagonParserException("No file extension in file name.");

        for (WagonParser parser : parsers) {
            if (parser.isFileExtensionSupported(fileExtension.get())) {
                Path filePath = rootLocation.resolve(filename).normalize().toAbsolutePath();
                return getWagonFromDummy(parser.parseWagons(filePath), operationStationEsr, user);
            }
        }

        throw new WagonParserException("Unsupported file extension");
    }

    private List<Wagon> getWagonFromDummy(List<DummyWagonFromParser> dummies, int operationStationEsr, User user) {
        List<Wagon> wagons = new ArrayList<>();
        Station operationStation = stationRepository.findStationByEsr(operationStationEsr).orElseThrow(() ->
                new WagonParserException("There is no operation station with esr %d."
                        .formatted(operationStationEsr)));
        WagonStatus wagonStatus = wagonStatusService.getOrCreateAndGetWagonStatusByName("Ближний подход");

        for (DummyWagonFromParser dummyWagon : dummies) {
            long wagonId = dummyWagon.getWagonId();
            int weight = dummyWagon.getWeight();

            Station destinationStation = stationRepository.findStationByEsr(dummyWagon.getDestinationStationEsr())
                    .orElse(new Station(dummyWagon.getDestinationStationEsr(), "UNRECOGNISED STATION", "UNNAMED"));
            Freight freight = freightRepository.findFreightByEtsng(dummyWagon.getFreightEtsng())
                    .orElseThrow(() ->
                            new WagonParserException("There is no freight with etsng %d."
                                    .formatted(dummyWagon.getFreightEtsng())));
            Consignee consignee = consigneeRepository.findConsigneeById(dummyWagon.getConsigneeId())
                    .orElseThrow(() ->
                            new WagonParserException("There is no consignee with id %d."
                                    .formatted(dummyWagon.getConsigneeId())));

            Wagon wagon = Wagon.builder()
                    .id(wagonId)
                    .destinationStation(destinationStation)
                    .operationStation(operationStation)
                    .freight(freight)
                    .consignee(consignee)
                    .weight(weight)
                    .wagonStatus(wagonStatus)
                    .updatedByUser(user)
                    .build();

            wagons.add(wagon);
        }
        return wagons;
    }
}
