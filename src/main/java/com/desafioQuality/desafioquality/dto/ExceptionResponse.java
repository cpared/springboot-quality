package com.desafioQuality.desafioquality.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    private String message;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date timestamp;
    private String details;
}
