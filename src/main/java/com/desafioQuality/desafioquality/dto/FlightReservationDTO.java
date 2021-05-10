package com.desafioQuality.desafioquality.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationDTO {

    @NotNull(message = "dateTo cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateFrom;

    @NotNull(message = "dateTo cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateTo;

    @NotNull(message = "dateTo cannot be null")
    private String origin;

    @NotNull(message = "dateTo cannot be null")
    private String destination;

    @NotNull(message = "dateTo cannot be null")
    private String flightNumber;

    @NotNull(message = "dateTo cannot be null")
    @Min(1)
    @Max(4)
    private int seats;

    @Pattern(regexp = "^Economy|Business")
    private String seatType;

    @Valid
    private List<PersonDTO> people;

    @Valid
    private PaymentMethodDTO paymentMethod;
}
