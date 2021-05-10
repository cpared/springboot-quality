package com.desafioQuality.desafioquality.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationResponseDTO {

    private String userName;
    private double amount;
    private double interest;
    private double total;
    private FlightReservationDTO flightReservation;
    private StatusCodeDTO statusCode;
}
