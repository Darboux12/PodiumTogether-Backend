package com.podium.validation.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ValidationHandler {

    public static boolean isTextEmpty(String text){
        return text.isEmpty();
    }

    public static boolean isValidDate(String date) {

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

    public static boolean isNull(Object object){
        return object == null;
    }

    public static boolean isDateInThePast(String date) throws ParseException {

        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms")
                .parse(date).before(new Date());





    }

    public static boolean isDateInTheFuture(String date) throws ParseException {

        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms")
                .parse(date).after(new Date());
    }

    public static boolean isDateInThePast(Date date) throws ParseException {
        return date.before(new Date());
    }

    public static boolean isDateInTheFuture(Date date) throws ParseException {

        return date.after(new Date());
    }

    public static boolean isLongerThan(String text, int lengthLimit){
        return text.length() > lengthLimit;
    }

    public static boolean isShorterThan(String text, int lengthLimit){
        return text.length() < lengthLimit;
    }

    public static  boolean isImageContentType(String imageType, List<String> allowedTypes){
        return allowedTypes.contains(imageType);
    }

    public static boolean isIntNumberEmpty(int number){
        return number == 0;
    }

    public boolean isDoubleNumberEmpty(double number){
        return number == 0;
    }

    public static boolean isTextNumeric(String text){
        return StringUtils.isNumeric(text);
    }

    public static boolean isEmailValid(String email){

        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);

    }
}
