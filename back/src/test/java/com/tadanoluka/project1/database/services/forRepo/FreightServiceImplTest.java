package com.tadanoluka.project1.database.services.forRepo;

import com.tadanoluka.project1.database.entity.Freight;
import com.tadanoluka.project1.database.entity.FreightsGroup;
import com.tadanoluka.project1.database.repository.FreightRepository;
import com.tadanoluka.project1.database.services.FreightServiceImpl;
import com.tadanoluka.project1.database.specifications.GenericSpecs;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FreightServiceImplTest {

    @Mock
    FreightRepository freightRepository;

    @InjectMocks
    FreightServiceImpl freightService;

    @Test
    void findAllPaged_PageRequestIsValid_ReturnsValidPageResponse() {
        Pageable pageRequest = PageRequest.of(0, 3);
        Page<Freight> page = getFreights();
        PageResponse<FreightDTO> expectedPageResponse = PageResponse.of(page.map(FreightDTO::new));
        Mockito.doReturn(page).when(this.freightRepository).findAll(pageRequest);

        PageResponse<FreightDTO> pageResponse = freightService.findAllPaged(pageRequest);

        Assertions.assertNotNull(pageResponse);
        Assertions.assertNotNull(pageResponse.getContent());
        Assertions.assertNotNull(pageResponse.getMetadata());
        Assertions.assertEquals(pageResponse.getContent(), expectedPageResponse.getContent());
        Assertions.assertEquals(pageResponse.getMetadata(), expectedPageResponse.getMetadata());
    }

    @Test
    void findAllFilteredPaged_PageRequestIsValid_ReturnsValidPageResponse() {
        Pageable pageRequest = PageRequest.of(0, 3);
        Specification<Freight> specification = new GenericSpecs<Freight>().getAllILikeFiltered(new HashMap<>());
        Page<Freight> page = getFreights();
        PageResponse<FreightDTO> expectedPageResponse = PageResponse.of(page.map(FreightDTO::new));
        Mockito.doReturn(page).when(this.freightRepository).findAll(specification, pageRequest);

        PageResponse<FreightDTO> pageResponse = freightService.findAllFilteredPaged(pageRequest, specification);

        Assertions.assertNotNull(pageResponse);
        Assertions.assertNotNull(pageResponse.getContent());
        Assertions.assertNotNull(pageResponse.getMetadata());
        Assertions.assertEquals(pageResponse.getContent(), expectedPageResponse.getContent());
        Assertions.assertEquals(pageResponse.getMetadata(), expectedPageResponse.getMetadata());
    }

    private static Page<Freight> getFreights() {
        FreightsGroup freightsGroup1 = new FreightsGroup(1, "Group 1");
        FreightsGroup freightsGroup2 = new FreightsGroup(1, "Group 2");
        Freight freight1 = new Freight(1, 1234567890, "TEST FREIGHT 1", "TEST 1", freightsGroup1);
        Freight freight2 = new Freight(2, 1234567891, "TEST FREIGHT 2", "TEST 2", freightsGroup2);
        Freight freight3 = new Freight(3, 1234567892, "TEST FREIGHT 3", "TEST 3", freightsGroup1);
        Page<Freight> page = new PageImpl<>(List.of(freight1, freight2, freight3));
        return page;
    }
}
