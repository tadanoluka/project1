package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.WagonStatus;
import com.tadanoluka.project1.database.repository.WagonStatusRepository;
import com.tadanoluka.project1.dto.StationDTO;
import com.tadanoluka.project1.dto.WagonStatusDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WagonStatusControllerTest {

    @Mock
    WagonStatusRepository wagonStatusRepository;

    @InjectMocks
    WagonStatusController wagonStatusController;

    @Test
    void handleGetAllWagonStatusesDTOsList_ReturnsValidResponseEntity() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        WagonStatus wagonStatus1 = new WagonStatus(1, "TEST WAGON_STATUS 1");
        WagonStatus wagonStatus2 = new WagonStatus(2, "TEST WAGON_STATUS 2");
        List<WagonStatusDTO> stationDTOs = List.of(new WagonStatusDTO(wagonStatus1), new WagonStatusDTO(wagonStatus2));
        Mockito.doReturn(stationDTOs).when(this.wagonStatusRepository).findAll(sort);


        ResponseEntity<?> responseEntity = this.wagonStatusController.handleGetAllWagonStatusesDTOsList();


        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(stationDTOs, responseEntity.getBody());
    }
}
