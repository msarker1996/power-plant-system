package com.brac.power.plant.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String status;
    private int code;
    private String message;
    private ErrorData data;
    private String timestamp;
    private String path;
}
