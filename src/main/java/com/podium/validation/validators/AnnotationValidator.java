package com.podium.validation.validators;

import com.podium.constant.PodiumLimits;
import com.podium.validation.annotation.*;
import com.podium.validation.exception.WrongAnnotationUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.sql.Connection;
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

    void podiumTextNotEmpty(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumTextNotEmpty.class)
            && this.isOptionalValueInvalid(object, field))

            if(field.getType().equals(String.class)) {
                if (ValidationHandler.getInstance().isTextEmpty((String) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " cannot be empty");
            }

            else
                throw new WrongAnnotationUsageException
                        ("PodiumTextNotEmpty must be used on String!");
    }

    void podiumNotNull(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumNotNull.class) &&
                this.isOptionalValueInvalid(object, field))
            if(ValidationHandler.getInstance().isNull(field.get(object)))
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, field.getName() +" cannot be empty");

    }

    void podiumDatePast(Object object, Field field) throws IllegalAccessException, ParseException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumDatePast.class))
            if(field.getType().equals(Date.class)){
                if(ValidationHandler.getInstance().isDateInTheFuture((Date) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() +" date cannot be in the future");
            }

             else throw new WrongAnnotationUsageException
                            ("PodiumDatePast must be used on Date!");

    }

    void podiumImageType(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumImageType.class))
            if(field.getType().equals(String.class)) {
                if (ValidationHandler.getInstance().isImageContentType((String) field.get(object), PodiumLimits.acceptedImagesTypes))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " is not allowed image type");
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumImageType must be used on String!");
    }

    void podiumDateFuture(Object object, Field field) throws IllegalAccessException, ParseException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumDateFuture.class))
            if(field.getType().equals(Date.class)) {
                if (ValidationHandler.getInstance().isDateInThePast((Date) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " date cannot be in the past");
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumDateFuture must be used on Date!");
    }

    void podiumLength(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumLength.class)
                && this.isOptionalValueInvalid(object, field))

            if(field.getType().equals(String.class)){

                String fieldValue = (String) field.get(object);
                int min = field.getAnnotation(PodiumLength.class).min();
                int max = field.getAnnotation(PodiumLength.class).max();

                    if (ValidationHandler.getInstance().isLongerThan(fieldValue, max))
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, field.getName() + " cannot be longer than " + max);

                    if (ValidationHandler.getInstance().isShorterThan(fieldValue, min))
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, field.getName() + " cannot be shorter than " + min);

            }

            else throw new WrongAnnotationUsageException
                    ("PodiumLength must be used on String!");

    }

    void podiumValidEmail(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumValidEmail.class))
            if(field.getType().equals(String.class)) {
                if (!ValidationHandler.getInstance().isEmailValid((String) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " is not valid email address");
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumValidEmail must be used on String!");

    }

    void podiumNumberInt(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumNumberInt.class) &&
                this.isOptionalValueInvalid(object, field))
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

            else throw new WrongAnnotationUsageException
                    ("PodiumNumberInt must be used on int!");




    }

    void podiumNumberDouble(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

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

            else throw new WrongAnnotationUsageException
                    ("PodiumNumberDouble must be used on double!");




    }

    void podiumTime(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumTime.class)
                & this.isOptionalValueInvalid(object, field))
            if(field.getType().equals(String.class)) {
                if (!ValidationHandler.getInstance().isTime((String) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " is not valid time format");
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumTime must be used on String!");


    }

    void podiumWeekDay(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumWeekDay.class))

            if(field.getType().equals(String.class)) {
                if (!ValidationHandler.getInstance().isWeekDay((String) field.get(object)))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " must be week day!");
            }

            else
                throw new WrongAnnotationUsageException
                        ("PodiumTextNotEmpty must be used on String!");
    }

    void podiumCollectionLength(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

       if(field.isAnnotationPresent(PodiumCollectionLength.class)
                && this.isOptionalValueInvalid(object, field))

            if(field.isAnnotationPresent(PodiumCollectionLength.class)){

                int min = field.getAnnotation(PodiumCollectionLength.class).min();
                int max = field.getAnnotation(PodiumCollectionLength.class).max();

            Collection collection = (Collection)field.get(object);

                if (ValidationHandler.getInstance().isCollectionLongerThan(collection,max))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " cannot be longer than " + max);

                if (ValidationHandler.getInstance().isCollectionShorterThan(collection, min))
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, field.getName() + " cannot be shorter than " + min);

            }

            else throw new WrongAnnotationUsageException
                    ("PodiumLength must be used on collection!");



    }

    private boolean isOptionalValueInvalid(Object object, Field field) throws IllegalAccessException {

        if(field.getType().equals(String.class)){
            String value = (String)field.get(object);
            return !field.isAnnotationPresent(PodiumOptionalValue.class) ||
                    !value.isBlank();
        }

        else return !field.isAnnotationPresent(PodiumOptionalValue.class) ||
                    (!field.get(object).equals(0) && field.get(object) != null);

    }

}
