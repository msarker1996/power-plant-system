package com.brac.power.plant.system.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "batteries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Batteries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String postCode;
    private Integer wattCapacity;

}
