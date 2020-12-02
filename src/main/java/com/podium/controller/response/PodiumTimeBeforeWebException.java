package com.podium.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PodiumTimeBeforeWebException extends ResponseStatusException {

    public PodiumTimeBeforeWebException(String valueBefore, String valueAfter){

        super(HttpStatus.CONFLICT,valueBefore + " cannot be later than " + valueAfter + " !");

    }
}
