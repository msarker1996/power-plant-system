package com.brac.power.plant.system.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BatteriesDto {

    @NotBlank(message = "name shouldn't be null")
    @Size(min = 2, max = 30, message = "name should be between 2 and 30 characters")
    private String name;

    @NotNull(message = "wattCapacity shouldn't be null")
    @PositiveOrZero(message = "wattCapacity should be zero or a positive number")
    private Integer wattCapacity;

    @NotBlank(message = "postCode shouldn't be null")
    @Size(min = 5, max = 10, message = "postCode should be between 5 and 10 characters")
    private String postCode;

}
