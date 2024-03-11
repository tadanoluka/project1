package com.tadanoluka.project1.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Getter
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private final Path rootLocation = Paths.get("C:\\testData\\");
    private final Path wagonsFileLocation = rootLocation.resolve("wagons").normalize().toAbsolutePath();

}
