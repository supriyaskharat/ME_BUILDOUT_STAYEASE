
package com.takehome.stayease.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takehome.stayease.dto.CreateHotelDto;
import com.takehome.stayease.dto.UpdateHotelDto;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.exception.custom_exception.NotFoundException;
import com.takehome.stayease.repo.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        log.info("Thread: {}, USER : {}, GetAllHotels", Thread.currentThread().getName(), SecurityContextHolder.getContext().getAuthentication().getName());
        return hotelRepository.findAll();
    }

    public Hotel createHotel(CreateHotelDto dto) {

        log.info("Thread: {}, USER : {}, trying Create hotel: {}", Thread.currentThread().getName(), SecurityContextHolder.getContext().getAuthentication().getName(), dto);

        Hotel hotel = Hotel.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .availableRooms(dto.getAvailableRooms())
                .totalRooms(dto.getTotalRooms())
                .build();

        log.info("Thread: {}, USER : {}, Create hotel successful: {}", Thread.currentThread().getName(), SecurityContextHolder.getContext().getAuthentication().getName(), dto);

        return hotelRepository.save(hotel);
    }

    public UpdateHotelDto updateHotel(UpdateHotelDto dto, int hotelId) {

        Hotel foundHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found"));

        if(dto.getName() != null)
            foundHotel.setName(dto.getName());
        if(dto.getLocation() != null)
            foundHotel.setLocation(dto.getLocation());
        if (dto.getDescription() != null)
            foundHotel.setDescription(dto.getDescription());
        if(dto.getAvailableRooms() != null)
            foundHotel.setAvailableRooms(dto.getAvailableRooms());
        if(dto.getTotalRooms() != null)
            foundHotel.setTotalRooms(dto.getTotalRooms());

        Hotel savedRecord = hotelRepository.save(foundHotel);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(savedRecord, UpdateHotelDto.class);

    }

    public void deleteHotelById(int hotelId){
        hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found"));
        hotelRepository.deleteById(hotelId);
    }
}
