package com.desafioQuality.desafioquality.dto;


import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingRequestDTO {

    @NotNull(message = "userName cannot be null")
    @Email
    private String userName;

    @NotNull(message = "booking cannot be null")
    @Valid
    private BookingDTO booking;
}
