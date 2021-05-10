package com.desafioQuality.desafioquality.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationRequestDTO {

    @NotNull(message = "userName cannot be null")
    private String userName;

    @NotNull(message = "flightReservation cannot be null")
    @Valid
    private FlightReservationDTO flightReservation;
}
