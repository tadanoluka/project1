package com.tadanoluka.project1.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final StorageProperties storageProperties;

    @Autowired
    public FileSystemStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    // Переписать
    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty())
                throw new StorageException("Failed to store empty file.");

            String originalFilename = file.getOriginalFilename();
            Optional<String> fileExtension = Optional.ofNullable(originalFilename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(originalFilename.lastIndexOf(".") + 1));

            String randomFileName = UUID.randomUUID() + "." + fileExtension.orElseThrow();
            Path destinationFile = storageProperties.getRootLocation()
                    .resolve(Paths.get(randomFileName))
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.getParent().equals(storageProperties.getRootLocation().toAbsolutePath()))
                throw new StorageException("Cannot store file outside current directory.");

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                return randomFileName;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    // store file and returns UUID filename
    @Override
    public String storeWagonFile(MultipartFile file) throws StorageException{
        try {
            if (file.isEmpty())
                throw new StorageEmptyFileException("Failed to store empty file.");

            String originalFilename = file.getOriginalFilename();
            Optional<String> fileExtension = Optional.ofNullable(originalFilename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(originalFilename.lastIndexOf(".") + 1));

            String randomFileName = UUID.randomUUID() + "." + fileExtension.orElseThrow();
            Path destinationFile = storageProperties.getWagonsFileLocation()
                    .resolve(Paths.get(randomFileName))
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.getParent().equals(storageProperties.getWagonsFileLocation().toAbsolutePath()))
                throw new StorageException("Cannot store file outside current directory.");

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                return randomFileName;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(storageProperties.getRootLocation(), 1)
                    .filter(path -> !path.equals(storageProperties.getRootLocation()))
                    .map(storageProperties.getRootLocation()::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return storageProperties.getRootLocation().resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(storageProperties.getRootLocation().toFile());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(storageProperties.getRootLocation());
            Files.createDirectories(storageProperties.getWagonsFileLocation());
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
