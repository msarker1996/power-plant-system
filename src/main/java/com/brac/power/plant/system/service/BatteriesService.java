package com.brac.power.plant.system.service;

import com.brac.power.plant.system.dto.BatteriesDto;
import com.brac.power.plant.system.dto.BatteryResponse;
import com.brac.power.plant.system.dto.Statistics;
import com.brac.power.plant.system.entity.Batteries;
import com.brac.power.plant.system.exception.ExistingException;
import com.brac.power.plant.system.repository.BatteriesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatteriesService {

    @Autowired
    private BatteriesRepository batteriesRepository;

    public Batteries saveBatteries(BatteriesDto batteriesDto) throws ExistingException {

        if (batteriesRepository.existsByName(batteriesDto.getName())) {
            throw new ExistingException("Name already exists !!");
        }
        Batteries batteries = new Batteries();
        batteries.setName(batteriesDto.getName());
        batteries.setPostCode(batteriesDto.getPostCode());
        batteries.setWattCapacity(batteriesDto.getWattCapacity());

        return batteriesRepository.save(batteries);
    }

    public BatteryResponse getBatteriesByPostCodeRange(String startPostCode, String endPostCode) {
        List<Batteries> batteries = batteriesRepository.findByPostCodeBetween(startPostCode, endPostCode);

        List<String> batteryNames = batteries.stream()
                .map(Batteries::getName)
                .collect(Collectors.toList());

        int totalWattCapacity = batteries.stream()
                .mapToInt(Batteries::getWattCapacity)
                .sum();

        double averageWattCapacity = batteries.isEmpty() ? 0 : totalWattCapacity / (double) batteries.size();

        BatteryResponse response = new BatteryResponse();
        response.setBatteryNames(batteryNames);

        Statistics stats = new Statistics();
        stats.setTotalWattCapacity(totalWattCapacity);
        stats.setAverageWattCapacity(averageWattCapacity);

        response.setStatistics(stats);

        return response;
    }

}
