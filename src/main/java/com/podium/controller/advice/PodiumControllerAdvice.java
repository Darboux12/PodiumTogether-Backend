package com.podium.controller.advice;

import com.podium.controller.status.PodiumResponse;
import com.podium.controller.validation.exception.*;
import com.podium.dal.files.exception.PodiumFileNotExistException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumFileUploadFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class PodiumControllerAdvice {

    @ExceptionHandler(PodiumEmptyTextException.class)
    public ResponseEntity<PodiumResponse> handlePodiumEmptyTextException(PodiumEmptyTextException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Empty Text Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<PodiumResponse> handleParseException(HttpMessageNotReadableException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Parse Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumDateFutureException.class)
    public ResponseEntity<PodiumResponse> handlePodiumDateFutureException(PodiumDateFutureException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Date Future Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumDatePastException.class)
    public ResponseEntity<PodiumResponse> handlePodiumDatePastException(PodiumDatePastException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Date Past Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumImageTypeException.class)
    public ResponseEntity<PodiumResponse> handlePodiumImageTypeException(PodiumImageTypeException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Image Type Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumInvalidEmailException.class)
    public ResponseEntity<PodiumResponse> handlePodiumInvalidEmailException(PodiumInvalidEmailException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Email Address Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumNullException.class)
    public ResponseEntity<PodiumResponse> handlePodiumNullException(PodiumNullException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Null Value Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTimeFormatException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTimeFormatException(PodiumTimeFormatException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Time Format Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLargeCollectionException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLargeCollectionException(PodiumTooLargeCollectionException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Collection Size Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLittleCollectionException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLittleCollectionException(PodiumTooLittleCollectionException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Collection Size Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLittleIntException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLittleIntException(PodiumTooLittleIntException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLargeIntException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLargeIntException(PodiumTooLargeIntException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLittleDouble.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLittleDouble(PodiumTooLittleDouble e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLargeDouble.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLargeDouble(PodiumTooLargeDouble e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Value Size Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooShortTextException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooShortTextException(PodiumTooShortTextException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Text Length Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumTooLongTextException.class)
    public ResponseEntity<PodiumResponse> handlePodiumTooLongTextException(PodiumTooLongTextException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Text Length Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumWeekDayException.class)
    public ResponseEntity<PodiumResponse> handlePodiumWeekDayException(PodiumWeekDayException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Week Day Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumEntityNotFoundException.class)
    public ResponseEntity<PodiumResponse> handlePodiumEntityNotFoundException(PodiumEntityNotFoundException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Object Not Found Error", HttpStatus.NOT_FOUND,
                        e.getMessage(),request.getDescription(false)), HttpStatus.NOT_FOUND
        );

    }

    @ExceptionHandler(PodiumEntityAlreadyExistException.class)
    public ResponseEntity<PodiumResponse> handlePodiumEntityAlreadyExistException(PodiumEntityAlreadyExistException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Object Already Exist Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<PodiumResponse> handleIOException(IOException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("Object Already Exist Error", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(PodiumFileUploadFailException.class)
    public ResponseEntity<PodiumResponse> handlePodiumFileNotExistException(PodiumFileUploadFailException e, WebRequest request) {

        return new ResponseEntity<>(
                this.createResponseMessage("File Not Exist", HttpStatus.CONFLICT,
                        e.getMessage(),request.getDescription(false)), HttpStatus.CONFLICT
        );

    }

    private PodiumResponse createResponseMessage(String title, HttpStatus status,String message, String description) {

        return new PodiumResponse(
                title,
                status.value(),
                new Date(),
                message,
                description
        );
    }

}
