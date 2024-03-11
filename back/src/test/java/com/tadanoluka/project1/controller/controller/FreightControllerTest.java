package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.controller.handler.pageable.PageableRequestHandler;
import com.tadanoluka.project1.controller.requests.pageable.PageableRequest;
import com.tadanoluka.project1.database.entity.Freight;
import com.tadanoluka.project1.database.entity.FreightsGroup;
import com.tadanoluka.project1.database.services.FreightService;
import com.tadanoluka.project1.dto.FreightDTO;
import com.tadanoluka.project1.dto.PageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FreightControllerTest {

    @Mock
    FreightService freightService;
    @Mock
    PageableRequestHandler<Freight, FreightDTO> pageableRequestHandler;

    @InjectMocks
    FreightController freightController;

    @Test
    void handleGetAllByPageableHandler_PageableRequestIsValid_ReturnsValidResponseEntity() {
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(0);
        pageableRequest.setSize(3);
        FreightsGroup freightsGroup1 = new FreightsGroup(1, "Group 1");
        FreightsGroup freightsGroup2 = new FreightsGroup(1, "Group 2");
        Freight freight1 = new Freight(1, 1234567890, "TEST FREIGHT 1", "TEST 1", freightsGroup1);
        Freight freight2 = new Freight(2, 1234567891, "TEST FREIGHT 2", "TEST 2", freightsGroup2);
        Freight freight3 = new Freight(3, 1234567892, "TEST FREIGHT 3", "TEST 3", freightsGroup1);
        Page<FreightDTO> page = new PageImpl<>(
                List.of(new FreightDTO(freight1), new FreightDTO(freight2), new FreightDTO(freight3)));
        PageResponse<FreightDTO> pageResponse = PageResponse.of(page);
        ResponseEntity<?> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pageResponse);
        Mockito.doReturn(expectedResponseEntity).when(this.pageableRequestHandler).getResponseEntity(this.freightService, pageableRequest);


        ResponseEntity<?> responseEntity = this.freightController.handleGetAllByPageableHandler(pageableRequest);


        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(pageResponse, responseEntity.getBody());

    }
}
