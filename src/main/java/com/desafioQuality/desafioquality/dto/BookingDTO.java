package com.desafioQuality.desafioquality.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDTO {

    @NotNull(message = "dateFrom cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateFrom;

    @NotNull(message = "dateTo cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateTo;

    @NotNull(message = "destination cannot be null")
    @NotBlank(message = "destination cannot be empty")
    private String destination;

    @NotNull(message = "hotelCode cannot be null")
    @NotBlank(message = "hotelCode cannot be empty")
    private String hotelCode;

    @NotNull(message = "peopleAmount cannot be null")
    @Min(1)
    @Max(4)
    private int peopleAmount;

    @NotNull(message = "roomType cannot be null")
    @NotBlank(message = "roomType cannot be empty")
    @Pattern(regexp = "^Single|Doble|Triple|Múltiple")
    private String roomType;

    @NotNull(message = "people cannot be null")
    @Valid
    private List<PersonDTO> people;

    @NotNull(message = "paymentMethod cannot be null")
    @Valid
    private PaymentMethodDTO paymentMethod;
}
