package com.desafioQuality.desafioquality.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {

    private String userName;
    private double amount;
    private double interest;
    private double total;
    private BookingDTO booking;
    private StatusCodeDTO statusCode;
}
