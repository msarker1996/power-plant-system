package com.brac.power.plant.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterErrorData {
    private List<ParameterErrorDetail> errors;

}
