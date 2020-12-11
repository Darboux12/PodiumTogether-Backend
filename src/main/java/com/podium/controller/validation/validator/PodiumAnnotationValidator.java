package com.podium.controller.validation.validator;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.*;
import com.podium.controller.validation.exception.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

@Component
public class PodiumAnnotationValidator {
    
    private PodiumValidationHandler validationHandler;

    public PodiumAnnotationValidator(PodiumValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }
    
    void podiumTextNotEmpty(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException, PodiumEmptyTextException {

        if(field.isAnnotationPresent(PodiumTextNotEmpty.class)
            && this.isNotOptionalValue(object, field))

            if(field.getType().equals(String.class)) {

                if (validationHandler.isTextEmpty((String) field.get(object)))
                    throw new PodiumEmptyTextException(field.getName());
            }

            else throw new WrongAnnotationUsageException
                        ("PodiumTextNotEmpty must be used on String!");
    }

    void podiumNotNull(Object object, Field field) throws IllegalAccessException {

        if(field.isAnnotationPresent(PodiumNotNull.class) &&
                this.isNotOptionalValue(object, field))

            if(validationHandler.isNull(field.get(object)))
                throw new PodiumNullException(field.getName());

    }

    void podiumDatePast(Object object, Field field) throws IllegalAccessException, ParseException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumDatePast.class))

            if(field.getType().equals(Date.class)){

                if(validationHandler.isDateInTheFuture((Date) field.get(object)))
                    throw new PodiumDatePastException(field.getName());
            }

             else throw new WrongAnnotationUsageException("PodiumDatePast must be used on Date!");

    }

    void podiumImageType(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumImageType.class))

            if(field.getType().equals(String.class)) {

                if (validationHandler.isImageContentType((String) field.get(object), PodiumLimits.acceptedImagesTypes))
                    throw new PodiumImageTypeException(field.getName());
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumImageType must be used on String!");
    }

    void podiumDateFuture(Object object, Field field) throws IllegalAccessException, ParseException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumDateFuture.class))

            if(field.getType().equals(Date.class)) {

                if (validationHandler.isDateInThePast((Date) field.get(object)))
                    throw new PodiumDateFutureException(field.getName());
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumDateFuture must be used on Date!");
    }

    void podiumLength(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumLength.class)
                && this.isNotOptionalValue(object, field))

            if(field.getType().equals(String.class)){

                String fieldValue = (String) field.get(object);
                int min = field.getAnnotation(PodiumLength.class).min();
                int max = field.getAnnotation(PodiumLength.class).max();

                    if (validationHandler.isLongerThan(fieldValue, max))
                        throw new PodiumTooLongTextException(field.getName(), max);

                    if (validationHandler.isShorterThan(fieldValue, min))
                        throw new PodiumTooShortTextException(field.getName(),min);

            }

            else throw new WrongAnnotationUsageException
                    ("PodiumLength must be used on String!");

    }

    void podiumValidEmail(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumValidEmail.class))
            if(field.getType().equals(String.class)) {
                if (!validationHandler.isEmailValid((String) field.get(object)))
                    throw new PodiumInvalidEmailException(field.getName());
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumValidEmail must be used on String!");

    }

    void podiumNumberInt(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumNumberInt.class) &&
                this.isNotOptionalValue(object, field))
            if(field.getType().equals(int.class)){

                int fieldValue = (int) field.get(object);
                int min = field.getAnnotation(PodiumNumberInt.class).min();
                int max = field.getAnnotation(PodiumNumberInt.class).max();

                if(validationHandler.isBiggerThanInt(fieldValue,max))
                    throw new PodiumTooLargeIntException(field.getName(),max);

                if(validationHandler.isSmallerThanInt(fieldValue,min))
                    throw new PodiumTooLittleIntException(field.getName(),min);

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

                if(validationHandler.isBiggerThanDouble(fieldValue,max))
                    throw new PodiumTooLargeDouble(field.getName(),max);

                if(validationHandler.isSmallerThanDouble(fieldValue,min))
                    throw new PodiumTooLittleDouble(field.getName(),min);

            }

            else throw new WrongAnnotationUsageException
                    ("PodiumNumberDouble must be used on double!");

    }

    void podiumTime(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumTime.class)
                & this.isNotOptionalValue(object, field))
            if(field.getType().equals(String.class)) {
                if (!validationHandler.isTime((String) field.get(object)))
                    throw new PodiumTimeFormatException(field.getName());
            }

            else throw new WrongAnnotationUsageException
                    ("PodiumTime must be used on String!");


    }

    void podiumWeekDay(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

        if(field.isAnnotationPresent(PodiumWeekDay.class))

            if(field.getType().equals(String.class)) {
                if (!validationHandler.isWeekDay((String) field.get(object)))
                    throw new PodiumWeekDayException(field.getName());
            }

            else
                throw new WrongAnnotationUsageException
                        ("PodiumTextNotEmpty must be used on String!");
    }

    void podiumCollectionLength(Object object, Field field) throws IllegalAccessException, WrongAnnotationUsageException {

       if(field.isAnnotationPresent(PodiumCollectionLength.class)
                && this.isNotOptionalValue(object, field))

            if(field.isAnnotationPresent(PodiumCollectionLength.class)){

                int min = field.getAnnotation(PodiumCollectionLength.class).min();
                int max = field.getAnnotation(PodiumCollectionLength.class).max();

            Collection collection = (Collection)field.get(object);

                if (validationHandler.isCollectionLongerThan(collection,max))
                    throw new PodiumTooLargeCollectionException(field.getName(),max);

                if (validationHandler.isCollectionShorterThan(collection, min))
                    throw new PodiumTooLittleCollectionException(field.getName(),min);

            }

            else throw new WrongAnnotationUsageException
                    ("PodiumLength must be used on collection!");



    }

    private boolean isNotOptionalValue(Object object, Field field) throws IllegalAccessException {

        if(field.getType().equals(String.class)){
            String value = (String)field.get(object);
            return !field.isAnnotationPresent(PodiumOptionalValue.class) ||
                    !value.isBlank();
        }

        else return !field.isAnnotationPresent(PodiumOptionalValue.class) ||
                    (!field.get(object).equals(0) && field.get(object) != null);

    }

}
