package com.podium.helper;

import com.podium.model.entity.News;
import com.podium.model.request.ContactRequest;
import com.podium.model.request.NewsRequest;
import com.podium.model.request.SignUpRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    private static SignUpRequest validSignUpRequestOne = new SignUpRequest();
    private static SignUpRequest validSignUpRequestTwo = new SignUpRequest();

    private static ContactRequest validContactRequest = new ContactRequest();

    private static NewsRequest validNewsRequest = new NewsRequest();

    private static News validNews = new News();

    public Constant() throws ParseException { }

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

    public static ContactRequest getValidContactRequest(){

        validContactRequest.setUserEmail("testusername@op.pl");
        validContactRequest.setMessage("Test contact message");
        validContactRequest.setSubject("Technical");

        return validContactRequest;

    }

    public static NewsRequest getValidNewsRequest(){

        validNewsRequest.setTitle("TestTitle");
        validNewsRequest.setFullText("TestFullText");
        validNewsRequest.setShortText("TestShortText");
        validNewsRequest.setLinkText("TestLinkText");

        return validNewsRequest;
    }



}
