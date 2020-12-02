package com.podium.controller.response;

import com.podium.model.dto.validation.annotation.PodiumDatePast;
import com.podium.model.dto.validation.annotation.PodiumWeekDay;
import com.podium.model.dto.validation.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class PodiumControllerAdvice {

    @ExceptionHandler(PodiumEmptyTextException.class)
    public ResponseEntity<PodiumResponse> handlePodiumEmptyTextException(PodiumEmptyTextException e) {

        String description = "Request cannot be performed because one of provided values is empty";

        return new ResponseEntity<>(
                this.createResponseMessage("Empty Text Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<PodiumResponse> handleParseException(HttpMessageNotReadableException e) {

        String description = "One of values has wrong type and cannot be parsed to dto";

        return new ResponseEntity<>(
                this.createResponseMessage("Parse Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumDateFutureException.class)
    public ResponseEntity<PodiumResponse> handlePodiumDateFutureException(PodiumDateFutureException e) {

        String description = "Given date cannot be in the future. Please provide other date";

        return new ResponseEntity<>(
                this.createResponseMessage("Date Future Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumDatePastException.class)
    public ResponseEntity<PodiumResponse> handlePodiumDatePastException(PodiumDatePastException e) {

        String description = "Given date cannot be in the past. Please provide other date";

        return new ResponseEntity<>(
                this.createResponseMessage("Date Past Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumImageTypeException.class)
    public ResponseEntity<PodiumResponse> handlePodiumImageTypeException(PodiumImageTypeException e) {

        String description = "Given format is not supported. Please provide another format";

        return new ResponseEntity<>(
                this.createResponseMessage("Image Type Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumInvalidEmailException.class)
    public ResponseEntity<PodiumResponse> handlePodiumInvalidEmailException(PodiumInvalidEmailException e) {

        String description = "Given email address is not valid. Please provide another email address";

        return new ResponseEntity<>(
                this.createResponseMessage("Email Address Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumNullException.class)
    public ResponseEntity<PodiumResponse> handlePodiumNullException(PodiumNullException e) {

        String description = "Given value cannot be empty. Please provide another value";

        return new ResponseEntity<>(
                this.createResponseMessage("Null Value Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTimeFormatException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTimeFormatException(PodiumTimeFormatException e) {

        String description = "Given time format in not valid. Please provide another time format";

        return new ResponseEntity<>(
                this.createResponseMessage("Time Format Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLargeCollectionException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLargeCollectionException(PodiumTooLargeCollectionException e) {

        String description = "Size of given collection is too big. Provide smaller collection";

        return new ResponseEntity<>(
                this.createResponseMessage("Collection Size Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLittleCollectionException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLittleCollectionException(PodiumTooLittleCollectionException e) {

        String description = "Size of given collection is too small. Provide bigger collection";

        return new ResponseEntity<>(
                this.createResponseMessage("Collection Size Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLittleIntException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLittleIntException(PodiumTooLittleIntException e) {

        String description = "Given value is to small. Provide bigger value";

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLargeIntException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLargeIntException(PodiumTooLargeIntException e) {

        String description = "Given value is to big. Provide smaller value";

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLittleDouble.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLittleDouble(PodiumTooLittleDouble e) {

        String description = "Given value is to small. Provide bigger value";

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLargeDouble.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLargeDouble(PodiumTooLargeDouble e) {

        String description = "Given value is to big. Provide smaller value";

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooShortTextException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooShortTextException(PodiumTooShortTextException e) {

        String description = "Given text value is to short. Provide longer text value";

        return new ResponseEntity<>(
                this.createResponseMessage("Text Length Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLongTextException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLongTextException(PodiumTooLongTextException e) {

        String description = "Given text value is to long. Provide shorter text value";

        return new ResponseEntity<>(
                this.createResponseMessage("Text Length Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumWeekDayException.class)
    public ResponseEntity<PodiumResponse> handlePodiumWeekDayException(PodiumWeekDayException e) {

        String description = "Given text value is not valid week day or weekend. Provide another text value";

        return new ResponseEntity<>(
                this.createResponseMessage("Week Day Error", HttpStatus.CONFLICT,
                        e.getMessage(), description), HttpStatus.CONFLICT
        );

    }

    private PodiumResponse createResponseMessage(String title, HttpStatus status,String message, String description){

        return new PodiumResponse(
                title,
                status.value(),
                new Date(),
                message,
                description
        );
    }








}
