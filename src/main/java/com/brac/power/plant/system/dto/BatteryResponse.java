package com.brac.power.plant.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatteryResponse {

    private List<String> batteryNames;
    private Statistics statistics;

}
