package com.podium.validation.validators;

import com.podium.constant.PodiumLimits;
import com.podium.validation.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

public class AnnotationValidator {

    private static AnnotationValidator instance;

    private AnnotationValidator() {}

    public static AnnotationValidator getInstance() {
        if(instance == null) {
            instance = new AnnotationValidator();
        }
        return instance;
    }

    void podiumCollectionTextNotEmpty(Object object) {

        // TODO: Detecting annotation on collection of primitives

        if(object.getClass().isAnnotationPresent(PodiumCollectionTextNotEmpty.class)){

            Object[] collectionObjects = ((Collection) object).toArray();

            // For every object in collection
            for (Object o : collectionObjects) {

                if(o.getClass().equals(String.class)){

                    System.out.println("Jestem stringiem w liscie");

                   /* if(ValidationHandler.getInstance().isTextEmpty((String)field.get(object)))
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, field.getName() +" cannot be empty"); */


                }


            }

        }

    }

    void podiumTextNotEmpty(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumTextNotEmpty.class))
            if(field.getType().equals(String.class))
                if(ValidationHandler.getInstance().isTextEmpty((String)field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be empty");

    }

    void podiumNotNull(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumNotNull.class))
            if(ValidationHandler.getInstance().isNull(field.get(object)))
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, field.getName() +" cannot be empty");

    }

    void podiumDatePast(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumDatePast.class))
            if(field.getType().equals(Date.class))
                if(ValidationHandler.getInstance().isDateInTheFuture((Date) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" date cannot be in the future");

    }

    void podiumImageType(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumImageType.class))
            if(field.getType().equals(String.class))
                if(ValidationHandler.getInstance().isImageContentType((String)field.get(object), PodiumLimits.acceptedImagesTypes))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" is not allowed image type");

    }

    void podiumDateFuture(Object object, Field field) throws IllegalAccessException, ParseException {

        if(field.isAnnotationPresent(PodiumDateFuture.class))
            if(field.getType().equals(Date.class))
                if(ValidationHandler.getInstance().isDateInThePast((Date) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" date cannot be in the past");

    }

    void podiumLength(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumLength.class))
            if(field.getType().equals(String.class)){

                String fieldValue = (String) field.get(object);
                int min = field.getAnnotation(PodiumLength.class).min();
                int max = field.getAnnotation(PodiumLength.class).max();

                if(!fieldValue.equals("")) {

                    if (ValidationHandler.getInstance().isLongerThan(fieldValue, max))
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, field.getName() + " cannot be longer than " + max);

                    if (ValidationHandler.getInstance().isShorterThan(fieldValue, min))
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, field.getName() + " cannot be shorter than " + min);
                }

            }


    }

    void podiumValidEmail(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumValidEmail.class))
            if(field.getType().equals(String.class))
                if(!ValidationHandler.getInstance().isEmailValid((String) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" is not valid email address");


    }

    void podiumNumberInt(Object object, Field field) throws IllegalAccessException{

        if(field.isAnnotationPresent(PodiumNumberInt.class))
            if(field.getType().equals(int.class)){

                int fieldValue = (int) field.get(object);
                int min = field.getAnnotation(PodiumNumberInt.class).min();
                int max = field.getAnnotation(PodiumNumberInt.class).max();

                if(ValidationHandler.getInstance().isBiggerThanInt(fieldValue,max))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be bigger than " + max);

                if(ValidationHandler.getInstance().isSmallerThanInt(fieldValue,min))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be smaller than " + min);


            }


    }

    void podiumNumberDouble(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumNumberDouble.class))
            if(field.getType().equals(double.class)){

                double fieldValue = (double) field.get(object);
                double min = field.getAnnotation(PodiumNumberDouble.class).min();
                double max = field.getAnnotation(PodiumNumberDouble.class).max();

                if(ValidationHandler.getInstance().isBiggerThanDouble(fieldValue,max))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be bigger than " + max);

                if(ValidationHandler.getInstance().isSmallerThanDouble(fieldValue,min))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" cannot be smaller than " + min);


            }


    }

    void podiumTime(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumTime.class))
            if(field.getType().equals(String.class))
                if(!ValidationHandler.getInstance().isTime((String)field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" is not valid time format");

    }
  
}
