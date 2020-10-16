package com.podium.validation.validators;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class PodiumValidationResponse {

    public static ResponseEntity EmptyValue(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.emptyValueHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.emptyValueStatus)
                .headers(headers).build();


    }

    public static ResponseEntity AlreadyExist(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.valueAlreadyExistHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.valueAlreadyExistStatus)
                .headers(headers)
                .body(paramName + "already exists");

    }

    public static ResponseEntity TooLong(String paramName, int limit){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.valueIsToLongHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.valueIsToLongStatus)
                .headers(headers)
                .body(paramName + "cannot be longer than " + limit);

    }

    public static ResponseEntity TooShort(String paramName, int limit){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.valueIsToShortHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.valueIsToShortStatus)
                .headers(headers)
                .body(paramName + "cannot be shorter than " + limit);

    }

    public static ResponseEntity InvalidDate(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.dateInvalidHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.dateInvalidStatus)
                .headers(headers)
                .body(paramName + "date is invalid");
    }

    public static ResponseEntity DateInThePast(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.dateInThePastHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.dateInThePastStatus)
                .headers(headers)
                .body(paramName + "cannot be in the past");
    }

    public static ResponseEntity DateInFuture(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.dateInTheFutureHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.dateInTheFutureStatus)
                .headers(headers)
                .body(paramName + "cannot be in the future");
    }

    public static ResponseEntity NonexistentValue(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.valueNotExistHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.valueNotExistStatus)
                .headers(headers)
                .body(paramName + "does not exist");

    }

    public static ResponseEntity NotFoundValue(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.valueNotFound,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.valueNotFoundStatus)
                .headers(headers)
                .body(paramName + "not found");



    }

    public static ResponseEntity NotAllowedImageType(String paramName){

        HttpHeaders headers = new HttpHeaders();
        headers.add(PodiumValidationConstants.notAllowedImageTypeHeader,paramName);

        return ResponseEntity
                .status(PodiumValidationConstants.notAllowedImageTypeHeaderStatus)
                .headers(headers)
                .body(paramName + " content type not allowed");

    }





}
