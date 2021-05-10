package com.desafioQuality.desafioquality.service;

import com.desafioQuality.desafioquality.dto.*;
import com.desafioQuality.desafioquality.exception.BaseException;
import com.desafioQuality.desafioquality.exception.CreditCardException;
import com.desafioQuality.desafioquality.exception.InvalidDestination;
import com.desafioQuality.desafioquality.exception.InvalidHotelException;
import com.desafioQuality.desafioquality.repository.IFlightsRepository;
import com.desafioQuality.desafioquality.util.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
public class FlightsService implements IFlightsService{

    @Autowired
    IFlightsRepository repository;

    @Override
    public List<FlightDTO> getAllFlights() {
        return repository.getAllFlights();
    }

    @Override
    public List<FlightDTO> getFlightsByFilter(Date dateTo, Date dateFrom, String origin, String destination) {
        List<FlightDTO> flights = this.repository.getAllFlights();

        flights = (List<FlightDTO>) MyFilter.filterByDateTo(dateTo, flights);
        flights = (List<FlightDTO>) MyFilter.filterByDateFrom(dateFrom, flights);
        flights = MyFilter.filterByOrigin(origin, flights);
        flights = (List<FlightDTO>) MyFilter.filterByDestination(destination, flights);
        return flights;
    }

    @Override
    public FlightReservationResponseDTO reservate(FlightReservationRequestDTO reservationBody) throws BaseException {
        FlightDTO flight = this.repository.getFlightByCode(reservationBody.getFlightReservation().getFlightNumber());

        validateSeatType(reservationBody.getFlightReservation().getSeatType(), flight);
        validateOriginAndDestination(reservationBody.getFlightReservation().getOrigin(), reservationBody.getFlightReservation().getDestination());
        return createResponse(reservationBody, flight);
    }

    private FlightReservationResponseDTO createResponse(FlightReservationRequestDTO reservationBody, FlightDTO flight){
        FlightReservationResponseDTO response = new FlightReservationResponseDTO();
        response.setFlightReservation(reservationBody.getFlightReservation());
        response.setUserName(reservationBody.getUserName());
        setTicketValues(response, flight, reservationBody.getFlightReservation().getPaymentMethod().getDues());
        setStatusCodeOK(response);
        return response;
    }

    private void setTicketValues(FlightReservationResponseDTO response, FlightDTO flight, Integer dues){
        Double interest = dues * 0.1;
        response.setAmount(flight.getPricePerPerson());
        response.setTotal(flight.getPricePerPerson() * response.getFlightReservation().getSeats() * interest);
        response.setInterest(interest);
    }

    private void setStatusCodeOK(FlightReservationResponseDTO response){
        StatusCodeDTO status = new StatusCodeDTO(HttpStatus.OK, "El proceso termino satisfactoriamente");
        response.setStatusCode(status);
    }

    private void validateSeatType(String seatType, FlightDTO flight) throws BaseException {
        if(flight == null) throw new InvalidHotelException("The flight doesn't exist");
        if(!seatType.equalsIgnoreCase(flight.getSeatType())) throw new InvalidHotelException("The seatType doesn't match with our repository");
    }

    private void validateOriginAndDestination(String anOrigin, String aDestination) throws BaseException {
        if(!this.repository.validateOriginAndDestination(anOrigin, aDestination)) throw new InvalidDestination(anOrigin+" or "+ aDestination+ "are not valid");
    }
}
