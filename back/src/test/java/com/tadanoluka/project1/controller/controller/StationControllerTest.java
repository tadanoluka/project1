package com.tadanoluka.project1.controller.controller;


import com.tadanoluka.project1.controller.handler.pageable.PageableRequestHandler;
import com.tadanoluka.project1.controller.requests.pageable.PageableRequest;
import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.database.services.StationService;
import com.tadanoluka.project1.dto.PageResponse;
import com.tadanoluka.project1.dto.StationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StationControllerTest {

    @Mock
    StationService stationService;
    @Mock
    UserRepository userRepository;
    @Mock
    PageableRequestHandler<Station, StationDTO> pageableRequestHandler;

    @InjectMocks
    StationController stationController;

    @Test
    void handleGetAllStationDTOList_ReturnsValidResponseEntity() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        Station station1 = new Station(1234567890, "TEST STATION 1", "TEST 1");
        Station station2 = new Station(1234567891, "TEST STATION 2", "TEST 2");
        List<StationDTO> stationDTOs = List.of(new StationDTO(station1), new StationDTO(station2));
        Mockito.doReturn(stationDTOs).when(this.stationService).findAll(sort);


        ResponseEntity<?> responseEntity = this.stationController.handleGetAllStationDTOList();


        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(stationDTOs, responseEntity.getBody());
    }

    @Test
    void handleGetStationDtoByStationEsr_StationEsrIsExists_ReturnsValidResponseEntity() {
        int stationEsr = 1234567890;
        Station station = new Station(stationEsr, "TEST STATION 1", "TEST 1");
        Optional<StationDTO> stationDTO = Optional.of(new StationDTO(station));
        Mockito.doReturn(stationDTO).when(this.stationService).findStationByEsr(stationEsr);


        ResponseEntity<?> responseEntity = this.stationController.handleGetStationDtoByStationEsr(stationEsr);


        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(stationDTO.get(), responseEntity.getBody());
    }

    @Test
    void handleGetStationDtoByStationEsr_StationEsrIsNotExists_Throws404ResponseStatusException() {
        int fakeStationEsr = 987654321;
        Optional<StationDTO> stationDTO = Optional.empty();
        Mockito.doReturn(stationDTO).when(this.stationService).findStationByEsr(fakeStationEsr);


        ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
            ResponseEntity<?> responseEntity = this.stationController.handleGetStationDtoByStationEsr(fakeStationEsr);
        });

        Assertions.assertNotNull(thrown);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void handleGetAllByPageableHandler_PageableRequestIsValid_ReturnsValidResponseEntity() {
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(0);
        pageableRequest.setSize(3);
        Station station1 = new Station(1234567890, "TEST STATION 1", "TEST 1");
        Station station2 = new Station(1234567891, "TEST STATION 2", "TEST 2");
        Station station3 = new Station(1234567892, "TEST STATION 3", "TEST 3");
        Page<StationDTO> page = new PageImpl<>(
                List.of(new StationDTO(station1), new StationDTO(station2), new StationDTO(station3)));
        PageResponse<StationDTO> pageResponse = PageResponse.of(page);
        ResponseEntity<?> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK)
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .body(pageResponse);
        Mockito.doReturn(expectedResponseEntity).when(this.pageableRequestHandler).getResponseEntity(this.stationService, pageableRequest);


        ResponseEntity<?> responseEntity = this.stationController.handleGetAllByPageableHandler(pageableRequest);


        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(pageResponse, responseEntity.getBody());
    }


}
