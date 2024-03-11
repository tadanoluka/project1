package com.tadanoluka.project1.storage;

public class StorageEmptyFileException extends StorageException{

    public StorageEmptyFileException(String message) {
        super(message);
    }

    public StorageEmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
