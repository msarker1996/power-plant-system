package com.brac.power.plant.system;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.brac.power.plant.system.dto.BatteriesDto;
import com.brac.power.plant.system.dto.BatteryResponse;
import com.brac.power.plant.system.entity.Batteries;
import com.brac.power.plant.system.exception.ExistingException;
import com.brac.power.plant.system.repository.BatteriesRepository;
import com.brac.power.plant.system.service.BatteriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class BatteryServiceTest {

    @Mock
    private BatteriesRepository batteriesRepository;

    @InjectMocks
    private BatteriesService batteryService;

    private BatteriesDto batteriesDto;
    private Batteries batteries;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        batteriesDto = new BatteriesDto();
        batteriesDto.setName("Battery1");
        batteriesDto.setPostCode("12345");
        batteriesDto.setWattCapacity(100);

        batteries = new Batteries();
        batteries.setName("Battery1");
        batteries.setPostCode("12345");
        batteries.setWattCapacity(100);
    }

    @Test
    void testSaveBatteries() throws ExistingException {
        when(batteriesRepository.existsByName(batteriesDto.getName())).thenReturn(false);
        when(batteriesRepository.save(any(Batteries.class))).thenReturn(batteries);

        Batteries savedBattery = batteryService.saveBatteries(batteriesDto);

        assertNotNull(savedBattery);
        assertEquals(batteriesDto.getName(), savedBattery.getName());
        assertEquals(batteriesDto.getPostCode(), savedBattery.getPostCode());
        assertEquals(batteriesDto.getWattCapacity(), savedBattery.getWattCapacity());
    }

    @Test
    void testSaveBatteries_NameAlreadyExists() {
        when(batteriesRepository.existsByName(batteriesDto.getName())).thenReturn(true);

        ExistingException exception = assertThrows(ExistingException.class, () -> {
            batteryService.saveBatteries(batteriesDto);
        });

        assertEquals("Name already exists !!", exception.getMessage());
    }

    @Test
    void testGetBatteriesByPostCodeRange() {
        Batteries battery1 = new Batteries();
        battery1.setName("Battery2");
        battery1.setPostCode("150");
        battery1.setWattCapacity(150);

        Batteries battery2 = new Batteries();
        battery2.setName("Battery3");
        battery2.setPostCode("180");
        battery2.setWattCapacity(200);

        List<Batteries> batteriesList = Arrays.asList(battery1, battery2);

        when(batteriesRepository.findByPostCodeBetween("100", "200")).thenReturn(batteriesList);

        BatteryResponse response = batteryService.getBatteriesByPostCodeRange("100", "200");

        assertNotNull(response);
        assertEquals(Arrays.asList("Battery2", "Battery3"), response.getBatteryNames());
        assertEquals(350, response.getStatistics().getTotalWattCapacity());
        assertEquals(175, response.getStatistics().getAverageWattCapacity());
    }
}
