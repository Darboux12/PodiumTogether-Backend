package com.podium.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumTimeBeforeException extends ResponseStatusException {

    public PodiumTimeBeforeException(String valueBefore, String valueAfter){

        super(HttpStatus.CONFLICT,valueBefore + " cannot be later than " + valueAfter + " !");

    }
}
