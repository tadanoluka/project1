package com.tadanoluka.project1.storage.parsers;

public class WagonParserEmptyFileException extends WagonParserException{

    public WagonParserEmptyFileException(String message) {
        super(message);
    }

    public WagonParserEmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
