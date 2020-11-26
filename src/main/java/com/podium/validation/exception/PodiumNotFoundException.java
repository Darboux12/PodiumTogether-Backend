package com.podium.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumNotFoundException extends ResponseStatusException {

    public PodiumNotFoundException(String value){

        super(HttpStatus.NOT_FOUND,value + " cannot be found!");

    }
}
