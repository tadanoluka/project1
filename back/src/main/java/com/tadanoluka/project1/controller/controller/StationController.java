package com.tadanoluka.project1.controller.controller;


import com.tadanoluka.project1.controller.handler.pageable.PageableRequestHandler;
import com.tadanoluka.project1.controller.requests.pageable.PageableRequest;
import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.database.services.StationService;
import com.tadanoluka.project1.dto.StationDTO;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetails;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class StationController {

    private final StationService stationService;
    private final UserRepository userRepository;
    private final PageableRequestHandler<Station, StationDTO> pageableRequestHandler;

    @Autowired
    public StationController(StationService stationService,
                             UserRepository userRepository,
                             PageableRequestHandler<Station, StationDTO> pageableRequestHandler) {
        this.stationService = stationService;
        this.userRepository = userRepository;
        this.pageableRequestHandler = pageableRequestHandler;
    }

    @GetMapping("/stations/all")
    public ResponseEntity<?> handleGetAllStationDTOList() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(stationService.findAll(sort));
    }

    @GetMapping("/stations/all/forUser")
    public ResponseEntity<?> handleGetAllStationDTOListForUser() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        CustomLdapUserDetails ldapUserDetails = (CustomLdapUserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<User> user = userRepository.findUserByUsername(ldapUserDetails.getUsername());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The user has not been found, try to re-login.");
        } return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stationService.findAll(sort, user.get()));
    }

    @GetMapping("/stations")
    public ResponseEntity<?> handleGetAllByPageableHandler(@ModelAttribute PageableRequest pageableRequest) {
        return pageableRequestHandler.getResponseEntity(stationService, pageableRequest);
    }

    @GetMapping("/stations/{stationEsr}")
    public ResponseEntity<?> handleGetStationDtoByStationEsr(@PathVariable int stationEsr) {
        Optional<StationDTO> stationDTO = stationService.findStationByEsr(stationEsr);
        if (stationDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(stationDTO.get());
    }
}
