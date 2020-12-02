package com.podium.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumAlreadyExistWebException extends ResponseStatusException {

    public PodiumAlreadyExistWebException(String value){

        super(HttpStatus.CONFLICT,value + " already exist!");

    }
}
