package com.tadanoluka.project1.controller.controller;


import com.tadanoluka.project1.controller.handler.pageable.PageableRequestHandler;
import com.tadanoluka.project1.controller.requests.pageable.PageableRequest;
import com.tadanoluka.project1.database.entity.Freight;
import com.tadanoluka.project1.database.services.FreightService;
import com.tadanoluka.project1.dto.FreightDTO;
import com.tadanoluka.project1.dto.StationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class FreightController {

    private final FreightService freightService;
    private final PageableRequestHandler<Freight, FreightDTO> pageableRequestHandler;

    @Autowired
    public FreightController(FreightService freightService,
                             PageableRequestHandler<Freight, FreightDTO> pageableRequestHandler) {
        this.freightService = freightService;
        this.pageableRequestHandler = pageableRequestHandler;
    }

    @GetMapping("/freights")
    public ResponseEntity<?> handleGetAllByPageableHandler(@ModelAttribute PageableRequest pageableRequest) {
        return pageableRequestHandler.getResponseEntity(freightService, pageableRequest);
    }

    @GetMapping("/freights/{freightEtsng}")
    public ResponseEntity<?> handleGetFreightDtoByEtsng(@PathVariable int freightEtsng) {
        Optional<FreightDTO> freightDTO = freightService.findFreightByEtsng(freightEtsng);
        if (freightDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(freightDTO.get());
    }
}
