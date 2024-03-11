package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.database.entity.WagonStatus;
import com.tadanoluka.project1.database.repository.WagonStatusRepository;
import com.tadanoluka.project1.dto.WagonStatusDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WagonStatusController {

    private final WagonStatusRepository wagonStatusRepository;

    @Autowired
    public WagonStatusController(WagonStatusRepository wagonStatusRepository) {
        this.wagonStatusRepository = wagonStatusRepository;
    }

    @GetMapping("/wagonStatuses/all")
    public ResponseEntity<?> handleGetAllWagonStatusesDTOsList() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(wagonStatusRepository.findAll(sort));
    }
}
