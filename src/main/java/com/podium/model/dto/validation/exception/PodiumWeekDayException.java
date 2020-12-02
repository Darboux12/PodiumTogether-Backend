package com.podium.model.dto.validation.exception;

public class PodiumWeekDayException extends RuntimeException {

    public PodiumWeekDayException(String valueName) {
        super(valueName + "is not valid week day!");
    }

}
