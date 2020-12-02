package com.podium.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumEmptyTextResponseException extends ResponseStatusException {

    public PodiumEmptyTextResponseException(String value){

        super(HttpStatus.CONFLICT,value + " cannot be empty!");

    }
}


