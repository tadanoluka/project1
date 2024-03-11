package com.tadanoluka.project1.controller.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class WagonUploadRequest {
    private MultipartFile file;
    private int operationStationEsr;
}
