package com.podium.helper;

import com.podium.model.dto.request.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    private static SignUpRequestDto validSignUpRequestDtoOne = new SignUpRequestDto();
    private static SignUpRequestDto validSignUpRequestDtoTwo = new SignUpRequestDto();
    private static ContactRequestDto validContactRequestDto = new ContactRequestDto();
    private static NewsRequestDto validNewsRequest = new NewsRequestDto();
    private static DisciplineRequestDto validDisciplineRequestDto = new DisciplineRequestDto();
    private static SubjectRequestDto validSubjectRequestDto = new SubjectRequestDto();
    private static CountryRequestDto validCountryRequestDto = new CountryRequestDto();

    public Constant() throws ParseException { }

    public static SignUpRequestDto getValidSignUpRequestDtoOne() throws ParseException {

        validSignUpRequestDtoOne.setUsername("test_username_one");
        validSignUpRequestDtoOne.setCountry("Poland");
        validSignUpRequestDtoOne.setEmail("testemail_one@gmail.com");
        Date birthdayOne = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1998-02-13");
        validSignUpRequestDtoOne.setBirthday(birthdayOne);
        validSignUpRequestDtoOne.setPassword("test_password_one");

        return validSignUpRequestDtoOne;
    }

    public static SignUpRequestDto getValidSignUpRequestDtoTwo() throws ParseException {

        validSignUpRequestDtoTwo.setUsername("test_username_Two");
        validSignUpRequestDtoTwo.setCountry("Poland");
        validSignUpRequestDtoTwo.setEmail("testemail_two@gmail.com");
        Date birthdayTwo = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1996-04-23");
        validSignUpRequestDtoTwo.setBirthday(birthdayTwo);
        validSignUpRequestDtoTwo.setPassword("test_password_two");

        return validSignUpRequestDtoTwo;
    }

    public static ContactRequestDto getValidContactRequestDto(){

        validContactRequestDto.setUserEmail("testusername@op.pl");
        validContactRequestDto.setMessage("Test contact message");
        validContactRequestDto.setSubject("Technical");

        return validContactRequestDto;

    }

    public static NewsRequestDto getValidNewsRequest(){

        validNewsRequest.setTitle("TestTitle");
        validNewsRequest.setText("TestFullText");
        validNewsRequest.setShortText("TestShortText");
        validNewsRequest.setLinkText("TestLinkText");

        return validNewsRequest;
    }

    public static SubjectRequestDto getValidSubjectRequestDto(){

        validSubjectRequestDto.setSubject("TestSubject");
        return validSubjectRequestDto;
    }

    public static DisciplineRequestDto getValidDisciplineRequestDto(){
        validDisciplineRequestDto.setDiscipline("TestDiscipline");
        return validDisciplineRequestDto;
    }

    public static CountryRequestDto getValidCountryRequestDto(){
        validCountryRequestDto.setCountryId("QQ");
        validCountryRequestDto.setNumCode(null);
        validCountryRequestDto.setName("TestCountry");
        validCountryRequestDto.setPrintableName("PrintableTestName");
        validCountryRequestDto.setIso3("QQQ");
        return validCountryRequestDto;

    }



}
