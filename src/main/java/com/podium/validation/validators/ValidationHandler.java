package com.podium.validation.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

 class ValidationHandler {

     private static ValidationHandler instance;

     private ValidationHandler() {}

     static ValidationHandler getInstance() {
        if(instance == null) {
            instance = new ValidationHandler();
        }
        return instance;
    }
    
    boolean isTextEmpty(String text) {
        return text.isEmpty();
    }

    boolean isValidDate(String date) {

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms");

        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    boolean isNull(Object object) {
        return object == null;
    }

    boolean isDateInThePast(String date) throws ParseException {

        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms")
                .parse(date).before(new Date());


    }

    boolean isDateInTheFuture(String date) throws ParseException {

        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms")
                .parse(date).after(new Date());
    }

    boolean isDateInThePast(Date date) throws ParseException {
        return date.before(new Date());
    }

    boolean isDateInTheFuture(Date date) throws ParseException {

        return date.after(new Date());
    }

    boolean isLongerThan(String text, int lengthLimit) {
        return text.length() > lengthLimit;
    }

    boolean isShorterThan(String text, int lengthLimit) {
        return text.length() < lengthLimit;
    }

    boolean isImageContentType(String imageType, List<String> allowedTypes) {
        return allowedTypes.contains(imageType);
    }

    boolean isIntNumberEmpty(int number) {
        return number == 0;
    }

    boolean isDoubleNumberEmpty(double number) {
        return number == 0;
    }

    boolean isTextNumericInt(String text) {

        double a = Double.parseDouble(text);

        return true;

    }

    boolean isEmailValid(String email) {

        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);

    }

    boolean isBiggerThanInt(int number, int limit) {
        return number > limit;
    }

    boolean isSmallerThanInt(int number, int limit) {
        return number < limit;
    }

    boolean isBiggerThanDouble(double number, double limit){
        return number > limit;
    }

    boolean isSmallerThanDouble(double number, double limit){
        return number < limit;
    }

    boolean isTime(String time){

        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }

        return true;
    }

}