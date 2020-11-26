package com.podium.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumAlreadyExistException extends ResponseStatusException {

    public PodiumAlreadyExistException(String value){

        super(HttpStatus.CONFLICT,value + " already exist!");

    }
}
