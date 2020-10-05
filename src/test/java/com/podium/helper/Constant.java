package com.podium.helper;

import com.podium.model.request.SignUpRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    private static SignUpRequest validSignUpRequestOne = new SignUpRequest();
    private static SignUpRequest validSignUpRequestTwo = new SignUpRequest();

    public Constant() throws ParseException {

        validSignUpRequestOne.setUsername("test_username_one");
        validSignUpRequestOne.setCountry("Poland");
        validSignUpRequestOne.setEmail("testemail_two@gmail.com");
        Date birthdayOne = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1998-02-13");
        validSignUpRequestOne.setBirthday(birthdayOne);
        validSignUpRequestOne.setPassword("test_password_one");

        validSignUpRequestTwo.setUsername("test_username_two");
        validSignUpRequestTwo.setCountry("Poland");
        validSignUpRequestTwo.setEmail("testemail_two@gmail.com");
        Date birthdayTwo = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1997-03-20");
        validSignUpRequestTwo.setBirthday(birthdayTwo);
        validSignUpRequestTwo.setPassword("test_password_two");

    }

    public static SignUpRequest getValidSignUpRequestOne() throws ParseException {

        validSignUpRequestOne.setUsername("test_username_one");
        validSignUpRequestOne.setCountry("Poland");
        validSignUpRequestOne.setEmail("testemail_one@gmail.com");
        Date birthdayOne = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1998-02-13");
        validSignUpRequestOne.setBirthday(birthdayOne);
        validSignUpRequestOne.setPassword("test_password_one");

        return validSignUpRequestOne;
    }

    public static SignUpRequest getValidSignUpRequestTwo() throws ParseException {

        validSignUpRequestTwo.setUsername("test_username_Two");
        validSignUpRequestTwo.setCountry("Poland");
        validSignUpRequestTwo.setEmail("testemail_two@gmail.com");
        Date birthdayTwo = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1996-04-23");
        validSignUpRequestTwo.setBirthday(birthdayTwo);
        validSignUpRequestTwo.setPassword("test_password_two");

        return validSignUpRequestTwo;
    }

}
