package com.podium.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumNotExistWebException extends ResponseStatusException {

    public PodiumNotExistWebException(String value){

        super(HttpStatus.NOT_FOUND, value + " does not exist!");

    }
}
