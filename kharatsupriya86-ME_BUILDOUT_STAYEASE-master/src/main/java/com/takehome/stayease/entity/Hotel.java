package com.takehome.stayease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Hotel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    Integer totalRooms;

    @Column(nullable = false)
    Integer availableRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel", orphanRemoval = true, cascade = CascadeType.ALL)
    List<Booking> bookings = new ArrayList<>();
}


