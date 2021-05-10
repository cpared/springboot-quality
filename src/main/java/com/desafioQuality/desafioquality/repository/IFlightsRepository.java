package com.desafioQuality.desafioquality.repository;

import com.desafioQuality.desafioquality.dto.FlightDTO;

import java.util.List;

public interface IFlightsRepository {

    List<FlightDTO> getAllFlights();

    FlightDTO getFlightByCode(String flightNumber);

    boolean validateOriginAndDestination(String anOrigin, String aDestination);
}
