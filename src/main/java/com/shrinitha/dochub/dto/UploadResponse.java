package com.shrinitha.dochub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponse {
    private String message;
    private Long documentId;
    private String s3Url;
}
