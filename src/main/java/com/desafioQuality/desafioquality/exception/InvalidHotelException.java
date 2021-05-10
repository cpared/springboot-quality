package com.desafioQuality.desafioquality.exception;

import org.springframework.http.HttpStatus;

public class InvalidHotelException extends BaseException{
    public InvalidHotelException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
