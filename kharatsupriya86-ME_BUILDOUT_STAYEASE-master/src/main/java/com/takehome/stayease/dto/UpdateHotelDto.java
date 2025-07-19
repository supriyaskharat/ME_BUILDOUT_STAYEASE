package com.takehome.stayease.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateHotelDto {

    Integer id;
    String name;
    Integer availableRooms;

    @JsonIgnore
    String location;

    @JsonIgnore
    String description;

    @JsonIgnore
    Integer totalRooms;
}

