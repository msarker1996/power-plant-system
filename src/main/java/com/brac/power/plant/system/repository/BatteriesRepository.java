package com.brac.power.plant.system.repository;

import com.brac.power.plant.system.entity.Batteries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatteriesRepository extends JpaRepository<Batteries,Long> {
    boolean existsByName(String name);

    List<Batteries> findByPostCodeBetween(String startPostCode, String endPostCode);

}
