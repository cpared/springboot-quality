package com.desafioQuality.desafioquality.service;

import com.desafioQuality.desafioquality.dto.*;
import com.desafioQuality.desafioquality.exception.BaseException;
import com.desafioQuality.desafioquality.exception.CreditCardException;
import com.desafioQuality.desafioquality.exception.InvalidDestination;
import com.desafioQuality.desafioquality.exception.InvalidHotelException;
import com.desafioQuality.desafioquality.repository.IHotelRepository;
import com.desafioQuality.desafioquality.util.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class HotelService implements IHotelService{

    @Autowired
    IHotelRepository repository;

    @Override
    public List<HotelDTO> getAllTheHotels() {
        return repository.getAllTheHotels();
    }

    @Override
    public List<HotelDTO> getHotelByDateRange(Date dateTo, Date dateFrom, String destination){
        List<HotelDTO> filteredHotels = repository.getAllTheHotels();

        filteredHotels = (List<HotelDTO>) MyFilter.filterByDateFrom(dateFrom, filteredHotels);
        filteredHotels = (List<HotelDTO>) MyFilter.filterByDateTo(dateTo, filteredHotels);
        filteredHotels = (List<HotelDTO>) MyFilter.filterByDestination(destination, filteredHotels);

        return filteredHotels;
    }

    @Override
    public BookingResponseDTO booking(BookingRequestDTO bookingBody) throws BaseException {
        HotelDTO hotel = this.repository.getHotelByCode(bookingBody.getBooking().getHotelCode());
        validateCreditCard(bookingBody.getBooking().getPaymentMethod().getType(), bookingBody.getBooking().getPaymentMethod().getDues());
        validateRoomType(bookingBody.getBooking().getPeopleAmount(), hotel);
        validateDestination(bookingBody.getBooking().getDestination());
        return createResponse(bookingBody, hotel);
    }

    private BookingResponseDTO createResponse(BookingRequestDTO bookingBody, HotelDTO hotel){
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBooking(bookingBody.getBooking());
        response.setUserName(bookingBody.getUserName());
        setTicketValues(response, hotel, bookingBody.getBooking().getPaymentMethod().getDues());
        setStatusCodeOK(response);
        return response;
    }

    private void setTicketValues(BookingResponseDTO response, HotelDTO hotel, Integer dues){
        Double interest = dues * 0.1;
        response.setAmount(hotel.getPricePerNight());
        response.setTotal(hotel.getPricePerNight() * this.repository.getHotelRooms(hotel) * getNights(hotel.getDateTo(), hotel.getDateFrom()) * interest);
        response.setInterest(interest);
    }

    private Integer getNights(Date dateTo, Date dateFrom){
        long diffInMillies = Math.abs(dateFrom.getTime() - dateTo.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (int) diff;
    }

    private void setStatusCodeOK(BookingResponseDTO response){
        StatusCodeDTO status = new StatusCodeDTO(HttpStatus.OK, "El proceso termino satisfactoriamente");
        response.setStatusCode(status);
    }

    private void validateRoomType(Integer peopleAmount, HotelDTO hotel) throws BaseException {
        if(hotel == null) throw new InvalidHotelException("The hotel doesn't exist");
        if(peopleAmount > this.repository.getHotelRooms(hotel)) throw new InvalidHotelException("The peopleAmont is more than the hotelRoomType");
    }

    private void validateDestination(String aDestination) throws BaseException {
        if(!this.repository.isValidDestination(aDestination)) throw new InvalidDestination(aDestination+" is an invalid destination");
    }

    private void validateCreditCard(String creditCard, int dues) throws CreditCardException {
        if(creditCard.equalsIgnoreCase("DEBIT") && dues != 1) throw new CreditCardException("Debit card can't have more than 1 due");
    }
}
