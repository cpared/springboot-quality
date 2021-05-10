package com.desafioQuality.desafioquality.controller;

import com.desafioQuality.desafioquality.dto.*;
import com.desafioQuality.desafioquality.exception.BaseException;
import com.desafioQuality.desafioquality.exception.CreditCardException;
import com.desafioQuality.desafioquality.exception.InvalidDateException;
import com.desafioQuality.desafioquality.service.IFlightsService;
import com.desafioQuality.desafioquality.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping(path="/api/v1")
public class Controller extends BaseController {

    @Autowired
    IHotelService hotelService;

    @Autowired
    IFlightsService flightsService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/hotels")
    public List<HotelDTO> getAllHotels() {
        return this.hotelService.getAllTheHotels();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/hotels", params = {"dateFrom", "dateTo", "destination"})
    public List<HotelDTO> getHotelsByFilter(@Valid QueryParamsDTO queryParams) throws Exception {
        return this.hotelService.getHotelByDateRange(
                queryParams.getDateTo(),
                queryParams.getDateFrom(),
                queryParams.getDestination());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path="/booking")
    public BookingResponseDTO booking(@Valid @RequestBody BookingRequestDTO bookingBody) throws BaseException {
        validateDateFromDateTo(bookingBody.getBooking().getDateFrom(), bookingBody.getBooking().getDateTo());
        validateCreditCard(bookingBody.getBooking().getPaymentMethod().getType(), bookingBody.getBooking().getPaymentMethod().getDues());
        return hotelService.booking(bookingBody);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/flights")
    public List<FlightDTO> getAllFlights() {
        return this.flightsService.getAllFlights();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/flights", params = {"dateFrom", "dateTo", "origin", "destination"})
    public List<FlightDTO> getFlightsByFilter(@Valid FlightsParamsDTO queryParams){
        return this.flightsService.getFlightsByFilter(
                queryParams.getDateTo(),
                queryParams.getDateFrom(),
                queryParams.getOrigin(),
                queryParams.getDestination());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path="/flight-reservation")
    public FlightReservationResponseDTO booking(@Valid @RequestBody FlightReservationRequestDTO reservationBody) throws BaseException {
        validateDateFromDateTo(reservationBody.getFlightReservation().getDateFrom(), reservationBody.getFlightReservation().getDateTo());
        validateCreditCard(reservationBody.getFlightReservation().getPaymentMethod().getType(), reservationBody.getFlightReservation().getPaymentMethod().getDues());
        return flightsService.reservate(reservationBody);
    }

    private void validateDateFromDateTo(Date dateFrom, Date dateTo) throws InvalidDateException {
        if(dateFrom.after(dateTo)) throw new InvalidDateException("DateFrom is after DateTo");
    }

    private void validateCreditCard(String creditCard, int dues) throws CreditCardException {
        if(creditCard.equalsIgnoreCase("DEBIT") && dues != 1) throw new CreditCardException("Debit card can't have more than 1 due");
    }

}




