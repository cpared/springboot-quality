package com.desafioQuality.desafioquality.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonDTO {

    @JsonAlias({"DNI", "dni"})
    @NotNull(message = "DNI cannot be null")
    private int DNI;

    @JsonAlias({"Nombre", "name"})
    @NotNull(message = "Name cannot be null")
    private String name;

    @JsonAlias({"Apellido", "lastname"})
    @NotNull(message = "Lastname cannot be null")
    private String lastname;

    @JsonAlias({"Fecha Nacimiento", "birthDate"})
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "BirthDate cannot be null")
    private Date birthDate;

    @JsonAlias({"E-mail", "mail"})
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String mail;

}
