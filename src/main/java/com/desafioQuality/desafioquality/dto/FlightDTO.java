package com.desafioQuality.desafioquality.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightDTO implements IFiltered {

    @JsonAlias({"Nro Vuelo", "flightNumber"})
    @NotNull(message = "flightNumber cannot be null")
    private String flightNumber;

    @JsonAlias({"Origen", "origin"})
    @NotNull(message = "origin cannot be null")
    private String origin;

    @JsonAlias({"Destino", "destination"})
    @NotNull(message = "destination cannot be null")
    private String destination;

    @JsonAlias({"Tipo Asiento", "seatType"})
    @NotNull(message = "seatType cannot be null")
    private String seatType;

    @JsonAlias({"Precio por persona", "pricePerPerson"})
    @NotNull(message = "pricePerPerson cannot be null")
    private double pricePerPerson;

    @JsonAlias({"Fecha ida", "dateFrom"})
    @NotNull(message = "dateFrom cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateFrom;

    @JsonAlias({"Fecha Vuelta", "dateTo"})
    @NotNull(message = "dateTo cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateTo;
}
