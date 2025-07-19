package com.takehome.stayease.service;

import com.takehome.stayease.dto.CreateBookingDto;
import com.takehome.stayease.entity.Booking;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.entity.User;
import com.takehome.stayease.exception.custom_exception.NotFoundException;
import com.takehome.stayease.repo.BookingRepository;
import com.takehome.stayease.repo.HotelRepository;
import com.takehome.stayease.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;


    public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }


    public CreateBookingDto createBooking(CreateBookingDto dto, Integer hotelId) {

        Hotel foundHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found"));
        User foundUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NotFoundException("User not found"));

        if(foundHotel.getAvailableRooms() == 0)
            throw new NotFoundException("No rooms available");

        LocalDate currentDate = LocalDate.now();
        LocalDate checkInDate = dto.getCheckInDate();
        LocalDate checkOutDate = dto.getCheckOutDate();

        if(checkInDate.isEqual(currentDate) || checkInDate.isBefore(currentDate) || checkInDate.isAfter(checkOutDate))
            throw new NotFoundException("Invalid check-in or check-out date");

        Booking userBooking = Booking.builder()
                .hotel(foundHotel)
                .user(foundUser)
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .build();

        foundUser.getBookings().add(userBooking);
        foundHotel.getBookings().add(userBooking);

        foundHotel.setAvailableRooms(foundHotel.getAvailableRooms() - 1);
        hotelRepository.save(foundHotel);

        Booking savedBooking = bookingRepository.save(userBooking);

        dto.setHotelId(hotelId);
        dto.setBookingId(savedBooking.getBookingId());

        return dto;
    }

    public CreateBookingDto getBookingById(Integer bookingId) {

        Booking foundBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));

        return CreateBookingDto.builder()
                .bookingId(foundBooking.getBookingId())
                .hotelId(foundBooking.getHotel().getId())
                .checkInDate(foundBooking.getCheckInDate())
                .checkOutDate(foundBooking.getCheckOutDate())
                .build();
    }

    public void cancelBooking(Integer bookingId) {

        Booking foundBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        bookingRepository.delete(foundBooking);
    }
}
