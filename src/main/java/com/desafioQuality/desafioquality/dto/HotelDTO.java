package com.desafioQuality.desafioquality.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotelDTO implements IFiltered {

    @JsonProperty("Código Hotel")
    @NotNull(message = "hotelCode cannot be null")
    private String hotelCode;

    @JsonProperty("Nombre")
    @NotNull(message = "name cannot be null")
    private String name;

    @JsonAlias({"Disponible Desde", "dateFrom"})
    @NotNull(message = "dateFrom cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date dateFrom;

    @JsonAlias({"Disponible hasta", "dateTo"})
    @NotNull(message = "dateTo cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date dateTo;

    @JsonProperty("Tipo de Habitación")
    @NotNull(message = "roomType cannot be null")
    private String roomType;

    @JsonProperty("Lugar/Ciudad")
    @NotNull(message = "destination cannot be null")
    private String destination;

    @JsonProperty("Precio por noche")
    @NotNull(message = "pricePerNight cannot be null")
    private double pricePerNight;

    @JsonProperty("Reservado")
    @NotNull(message = "isReserved cannot be null")
    private boolean isReserved;

}
