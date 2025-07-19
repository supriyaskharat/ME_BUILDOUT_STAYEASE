package com.takehome.stayease.controller;

import com.takehome.stayease.dto.CreateHotelDto;
import com.takehome.stayease.dto.UpdateHotelDto;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // Get All hotels public
    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    // Create hotel
    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<?> createHotel(@Valid @RequestBody CreateHotelDto dto) {

        Hotel hotel = hotelService.createHotel(dto);
        return ResponseEntity.ok(hotel);
    }

    // Update the hotel
    @Secured("HOTEL_MANAGER")
    @PutMapping("/{hotelId}")
    public ResponseEntity<?> updateHotel(@PathVariable int hotelId, @RequestBody UpdateHotelDto dto){

        UpdateHotelDto res = hotelService.updateHotel(dto, hotelId);

        return ResponseEntity.ok().body(res);
    }

    // Delete hotel
    @Secured("ADMIN")
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<?> deleteHotel(@PathVariable int hotelId) {

        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.status(204).build();
    }
}

