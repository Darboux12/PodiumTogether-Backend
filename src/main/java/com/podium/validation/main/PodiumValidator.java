package com.podium.validation.main;

import com.podium.validation.annotation.*;
import com.podium.constant.PodiumLimits;
import com.podium.validation.validators.ValidationHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;

public class PodiumValidator {

    private static PodiumValidator instance;

    private PodiumValidator() {}

    public static PodiumValidator getInstance() {
        if(instance == null) {
            instance = new PodiumValidator();
        }
        return instance;
    }

    public void validateRequestBody(Object object){

        Class requestClass = object.getClass();

        for(Field field : requestClass.getDeclaredFields()){

            field.setAccessible(true);

            try {
                this.podiumTextNotEmpty(object,field);
                this.podiumNotNull(object,field);
                this.podiumDatePast(object,field);
                this.podiumImageType(object,field);
                this.podiumDateFuture(object,field);
                this.podiumLength(object,field);
                this.podiumValidEmail(object,field);
                this.podiumNumberInt(object,field);
                this.podiumNumberDouble(object,field);

            } catch (IllegalAccessException | ParseException e) {
                e.printStackTrace();


            }


        }
        }

    private void podiumTextNotEmpty(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumTextNotEmpty.class))
            if(field.getType().equals(String.class))
                if(ValidationHandler.isTextEmpty((String)field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be empty");

    }

    private void podiumNotNull(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumNotNull.class))
                if(ValidationHandler.isNull(field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be empty");

    }

    private void podiumDatePast(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumDatePast.class))
            if(field.getType().equals(Date.class))
                if(ValidationHandler.isDateInTheFuture((Date) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" date cannot be in the future");

    }

    private void podiumImageType(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumImageType.class))
            if(field.getType().equals(String.class))
                if(ValidationHandler.isImageContentType((String)field.get(object), PodiumLimits.acceptedImagesTypes))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" is not allowed image type");

    }

    private void podiumDateFuture(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumDateFuture.class))
            if(field.getType().equals(Date.class))
                if(ValidationHandler.isDateInThePast((Date) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" date cannot be in the past");

    }

    private void podiumLength(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumLength.class))
            if(field.getType().equals(String.class)){

                String fieldValue = (String) field.get(object);
                int min = field.getAnnotation(PodiumLength.class).min();
                int max = field.getAnnotation(PodiumLength.class).max();

                if(ValidationHandler.isLongerThan(fieldValue,max))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be longer than " + max);

                if(ValidationHandler.isShorterThan(fieldValue,min))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be shorter than " + min);


            }


    }

    private void podiumValidEmail(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumValidEmail.class))
            if(field.getType().equals(String.class))
                if(!ValidationHandler.isEmailValid((String) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" is not valid email address");


    }

    private void podiumNumberInt(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumNumberInt.class))
            if(field.getType().equals(int.class)){

                int fieldValue = (int) field.get(object);
                int min = field.getAnnotation(PodiumNumberInt.class).min();
                int max = field.getAnnotation(PodiumNumberInt.class).max();

                if(ValidationHandler.isBiggerThanInt(fieldValue,max))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be bigger than " + max);

                if(ValidationHandler.isSmallerThanInt(fieldValue,min))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be smaller than " + min);


            }


    }

    private void podiumNumberDouble(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumNumberDouble.class))
            if(field.getType().equals(double.class)){

                double fieldValue = (double) field.get(object);
                double min = field.getAnnotation(PodiumNumberDouble.class).min();
                double max = field.getAnnotation(PodiumNumberDouble.class).max();

                if(ValidationHandler.isBiggerThanDouble(fieldValue,max))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be bigger than " + max);

                if(ValidationHandler.isSmallerThanDouble(fieldValue,min))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be smaller than " + min);


            }


    }












}
