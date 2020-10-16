package com.podium.validation.validators;

public class PodiumValidationConstants {

    public static final String emptyValueHeader = "Empty-Value";
    public static final int emptyValueStatus =  409;

    public static final String dateInvalidHeader = "Invalid-Date";
    public static final int dateInvalidStatus =  409;

    public static final String dateInThePastHeader = "Passed-Date";
    public static final int dateInThePastStatus =  409;

    public static final String dateInTheFutureHeader = "Future-Date";
    public static final int dateInTheFutureStatus =  409;

    public static final String valueAlreadyExistHeader = "Already-Exist";
    public static final int valueAlreadyExistStatus =  409;

    public static final String valueIsToLongHeader = "Max-Length-Limit";
    public  static final int valueIsToLongStatus =  409;

    public static final String valueIsToShortHeader = "Min-Length-Limit";
    public static final int valueIsToShortStatus =  409;

    public static final String valueNotExistHeader = "Nonexistent-Value";
    public static final int valueNotExistStatus =  409;

    public static final String valueNotFound = "Notfound-Value";
    public static final int valueNotFoundStatus =  404;

    public static final String notAllowedImageTypeHeader = "Notfound-Value";
    public static final int notAllowedImageTypeHeaderStatus =  409;





}
