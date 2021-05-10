package com.desafioQuality.desafioquality.repository;

import com.desafioQuality.desafioquality.dto.HotelDTO;

import java.util.List;

public interface IHotelRepository {

    List<HotelDTO> getAllTheHotels();

    HotelDTO getHotelByCode(String code);

    public Integer getHotelRooms(HotelDTO hotel);

    public boolean isValidDestination(String aDestination);
}
