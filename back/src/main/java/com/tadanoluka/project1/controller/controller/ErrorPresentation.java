package com.tadanoluka.project1.controller.controller;

import lombok.Data;


@Data
public class ErrorPresentation {
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorPresentation(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
