package com.brac.power.plant.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterErrorDetail {
    private String parameter;
    private String message;
}
