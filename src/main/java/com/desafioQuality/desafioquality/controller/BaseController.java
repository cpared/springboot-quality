package com.desafioQuality.desafioquality.controller;

import com.desafioQuality.desafioquality.dto.ExceptionResponse;
import com.desafioQuality.desafioquality.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.Date;

public abstract class BaseController {

        @ExceptionHandler(BaseException.class)
        public ResponseEntity<ExceptionResponse> handleExceptionMyException(BaseException ex, WebRequest request){
                return new ResponseEntity<>(new ExceptionResponse(
                        ex.getMessage(),
                        new Date(),
                        request.getDescription(false)),
                        HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, ConstraintViolationException.class, BindException.class})
        public final ResponseEntity<Object> handleAnnotationException(Exception ex, WebRequest request){
                String[] message = ex.getMessage().split(";");
                return new ResponseEntity<>(new ExceptionResponse(
                        message[message.length - 1],
                        new Date(),
                        request.getDescription(false)),
                        HttpStatus.BAD_REQUEST);
        }

}
