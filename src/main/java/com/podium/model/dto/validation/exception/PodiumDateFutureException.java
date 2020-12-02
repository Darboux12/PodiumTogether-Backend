package com.podium.model.dto.validation.exception;

public class PodiumDateFutureException extends RuntimeException {

    public PodiumDateFutureException(String valueName) {
        super(valueName + "cannot be in the future!");
    }

}
