package com.podium.controller.validation.exception;

public class PodiumDateFutureException extends RuntimeException {

    public PodiumDateFutureException(String valueName) {
        super(valueName + " cannot be in the future!");
    }

}
