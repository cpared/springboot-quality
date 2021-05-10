package com.desafioQuality.desafioquality.service;

import com.desafioQuality.desafioquality.dto.BookingRequestDTO;
import com.desafioQuality.desafioquality.dto.BookingResponseDTO;
import com.desafioQuality.desafioquality.dto.FlightDTO;
import com.desafioQuality.desafioquality.dto.HotelDTO;
import com.desafioQuality.desafioquality.exception.BaseException;
import com.desafioQuality.desafioquality.exception.InvalidHotelException;

import java.util.Date;
import java.util.List;

public interface IHotelService {

    List<HotelDTO> getAllTheHotels();
    List<HotelDTO> getHotelByDateRange(Date dateTo, Date dateFrom, String destination) throws Exception;
    BookingResponseDTO booking(BookingRequestDTO bookingBody) throws InvalidHotelException, BaseException;
}
