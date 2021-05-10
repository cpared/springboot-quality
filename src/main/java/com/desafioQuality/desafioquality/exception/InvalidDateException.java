package com.desafioQuality.desafioquality.exception;

import org.springframework.http.HttpStatus;

public class InvalidDateException extends BaseException{
    public InvalidDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
