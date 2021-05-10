package com.desafioQuality.desafioquality.exception;

import org.springframework.http.HttpStatus;

public class CreditCardException extends BaseException{
    public CreditCardException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
