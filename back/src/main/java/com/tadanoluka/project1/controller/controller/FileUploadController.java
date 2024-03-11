package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.data.SampleDataLoader;
import com.tadanoluka.project1.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class FileUploadController {

    private final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/file")
    public String handleFileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        logger.info("POST %s".formatted(file.getOriginalFilename()));
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    //    @ExceptionHandler(StorageFileNotFoundException.class)
    //    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    //        return ResponseEntity.notFound().build();
    //    }

}
