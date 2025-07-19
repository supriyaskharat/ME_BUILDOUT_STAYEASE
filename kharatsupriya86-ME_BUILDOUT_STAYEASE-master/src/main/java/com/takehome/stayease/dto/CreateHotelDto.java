package com.takehome.stayease.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateHotelDto {

    @NotBlank
    String name;

    String location = "";
    String description = "";
    Integer totalRooms = 0;
    Integer availableRooms = 0;
}

