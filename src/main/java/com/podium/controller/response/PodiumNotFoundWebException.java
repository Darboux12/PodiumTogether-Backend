package com.podium.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumNotFoundWebException extends ResponseStatusException {

    public PodiumNotFoundWebException(String value){

        super(HttpStatus.NOT_FOUND,value + " cannot be found!");

    }
}
