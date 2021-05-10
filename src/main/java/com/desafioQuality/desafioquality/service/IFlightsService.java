package com.desafioQuality.desafioquality.service;

import com.desafioQuality.desafioquality.dto.*;
import com.desafioQuality.desafioquality.exception.BaseException;

import java.util.Date;
import java.util.List;

public interface IFlightsService {

    List<FlightDTO> getAllFlights();

    List<FlightDTO> getFlightsByFilter(Date dateTo, Date dateFrom, String origin, String destination);

    FlightReservationResponseDTO reservate(FlightReservationRequestDTO reservationBody) throws BaseException;
}
