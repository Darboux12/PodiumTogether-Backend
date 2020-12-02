package com.podium.model.dto.validation.exception;

public class WrongAnnotationUsageException extends Exception {

    public WrongAnnotationUsageException(String errMessage){
        super(errMessage);
    }

}
