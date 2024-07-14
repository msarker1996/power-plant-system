package com.brac.power.plant.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterErrorResponse {
    private String status;
    private int code;
    private String message;
    private ParameterErrorData data;
    private String timestamp;
    private String path;
}
