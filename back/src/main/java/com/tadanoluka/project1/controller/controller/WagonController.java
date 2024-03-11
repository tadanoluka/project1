package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.controller.handler.pageable.PageableRequestHandler;
import com.tadanoluka.project1.controller.requests.pageable.PageableRequest;
import com.tadanoluka.project1.controller.requests.WagonUploadRequest;
import com.tadanoluka.project1.data.SampleDataLoader;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.entity.Wagon;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.database.services.WagonService;
import com.tadanoluka.project1.dto.WagonDTO;

import java.util.*;

import com.tadanoluka.project1.security.ldap.CustomLdapUserDetails;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsImpl;
import com.tadanoluka.project1.storage.StorageEmptyFileException;
import com.tadanoluka.project1.storage.StorageService;
import com.tadanoluka.project1.storage.parsers.DummyWaggonParser;
import com.tadanoluka.project1.storage.parsers.WagonParserException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class WagonController {

    private final WagonService wagonService;
    private final PageableRequestHandler<Wagon, WagonDTO> pageableRequestHandler;
    private final StorageService storageService;
    private final DummyWaggonParser dummyWaggonParser;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);

    @Autowired
    public WagonController(WagonService wagonService,
                           PageableRequestHandler<Wagon, WagonDTO> pageableRequestHandler,
                           StorageService storageService,
                           DummyWaggonParser dummyWaggonParser,
                           UserRepository userRepository) {
        this.wagonService = wagonService;
        this.pageableRequestHandler = pageableRequestHandler;
        this.storageService = storageService;
        this.dummyWaggonParser = dummyWaggonParser;
        this.userRepository = userRepository;
    }

    @GetMapping("/wagons")
    public ResponseEntity<?> getAllByPageableHandler(@Valid PageableRequest pageableRequest) {
        return pageableRequestHandler.getResponseEntity(wagonService, pageableRequest);
    }

    @PatchMapping("/wagons")
    public ResponseEntity<?> handleFileUpload(@ModelAttribute WagonUploadRequest wagonUploadRequest) {
//        logger.info("PATCH %s".formatted(file.getOriginalFilename()));
        System.out.println(wagonUploadRequest.getFile().getName());
        System.out.println(wagonUploadRequest.getOperationStationEsr());
        MultipartFile file = wagonUploadRequest.getFile();
        try {
            String filename = storageService.storeWagonFile(file);
//            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
            CustomLdapUserDetails ldapUserDetails = (CustomLdapUserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            Optional<User> user = userRepository.findUserByUsername(ldapUserDetails.getUsername());
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The user has not been found, try to re-login.");
            }
            List<Wagon> wagonList = dummyWaggonParser.parse(filename, wagonUploadRequest.getOperationStationEsr(), user.get());

            wagonService.loadWagons(wagonList);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Успех");

        } catch (StorageEmptyFileException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (WagonParserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/wagons/{wagonId}")
    public ResponseEntity<?> handleGetStationDtoByStationEsr(@PathVariable int wagonId) {
        Optional<WagonDTO> wagonDTO = wagonService.findWagonById(wagonId);
        if (wagonDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(wagonDTO.get());
    }
}
