package com.podium.controller.excepttion;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class CustomControllerAdvice {

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "One of values cannot be parsed!")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleParseException() {

    }
}
