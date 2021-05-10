package com.desafioQuality.desafioquality.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {

    @NotEmpty(message = "type cannot be empty")
    @NotNull(message = "type cannot be null")
    @Pattern(regexp = "^CREDIT|DEBIT")
    private String type;

    @NotEmpty(message = "number cannot be empty")
    @NotNull(message = "number cannot be null")
    @CreditCardNumber(ignoreNonDigitCharacters = true)
    private String number;

    @Min(value = 1, message = "dues must be greater or equals than 1")
    @Max(value = 12, message = "dues must be less or equals than 12")
    @NotNull(message = "dues cannot be null")
    private int dues;
}
