package com.podium.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumNotExistException extends ResponseStatusException {

    public PodiumNotExistException(String value){

        super(HttpStatus.NOT_FOUND, value + " does not exist!");

    }
}
