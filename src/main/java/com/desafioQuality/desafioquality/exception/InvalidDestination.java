package com.desafioQuality.desafioquality.exception;

import org.springframework.http.HttpStatus;

public class InvalidDestination extends BaseException{
    public InvalidDestination(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
