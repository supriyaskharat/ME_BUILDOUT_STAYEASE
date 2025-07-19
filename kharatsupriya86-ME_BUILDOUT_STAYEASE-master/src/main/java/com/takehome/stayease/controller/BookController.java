package com.takehome.stayease.controller;

import com.takehome.stayease.dto.CreateBookingDto;
import com.takehome.stayease.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookController {


    private final BookingService bookingService;

    public BookController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Secured("USER")
    @PostMapping("/{hotelId}")
    public ResponseEntity<?> createBooking(@Valid @RequestBody CreateBookingDto dto, @PathVariable Integer hotelId) {

        CreateBookingDto booking = bookingService.createBooking(dto, hotelId);

        return ResponseEntity.ok(booking);
    }

    @Secured("USER")
    @GetMapping("/{booking_id}")
    public ResponseEntity<?> getBooking(@PathVariable Integer booking_id) {

        CreateBookingDto booking = bookingService.getBookingById(booking_id);
        return ResponseEntity.ok(booking);
    }

    @Secured("HOTEL_MANAGER")
    @DeleteMapping("/{booking_id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Integer booking_id) {

        bookingService.cancelBooking(booking_id);
        return ResponseEntity.status(204).build();
    }

}

