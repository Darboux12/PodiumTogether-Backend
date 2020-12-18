package com.podium.controller.validation.exception;

public class PodiumWeekDayException extends RuntimeException {

    public PodiumWeekDayException(String valueName) {
        super(valueName + " is not valid week day!");
    }

}
