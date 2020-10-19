package com.podium.helper;

import com.podium.model.dto.request.*;
import com.sun.jdi.request.EventRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constant {

    private static SignUpRequestDto validSignUpRequestDtoOne = new SignUpRequestDto();
    private static SignUpRequestDto validSignUpRequestDtoTwo = new SignUpRequestDto();
    private static ContactRequestDto validContactRequestDto = new ContactRequestDto();
    private static NewsRequestDto validNewsRequest = new NewsRequestDto();
    private static DisciplineRequestDto validDisciplineRequestDto = new DisciplineRequestDto();
    private static SubjectRequestDto validSubjectRequestDto = new SubjectRequestDto();
    private static CountryRequestDto validCountryRequestDto = new CountryRequestDto();
    private static GenderRequestDto validGenderRequestDto = new GenderRequestDto();
    private static EventRequestDto validEventRequestDto = new EventRequestDto();
    private static CityRequestDto validCityRequestDto = new CityRequestDto();


    public Constant() throws ParseException { }

    public static SignUpRequestDto getValidSignUpRequestDtoOne() throws ParseException {

        validSignUpRequestDtoOne.setUsername("test_username_one");
        validSignUpRequestDtoOne.setCountry("POLAND");
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

    public static GenderRequestDto getValidGenderRequestDto(){
        validGenderRequestDto.setGender("TestGender");
        return validGenderRequestDto;
    }

    public static EventRequestDto getValidEventRequestDto() throws ParseException {

        validEventRequestDto.setTitle("TestEventTitle");

        Date dateFrom= new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss:ms").parse("2021-02-13 17:00:00:00");
        Date dateTo= new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss:ms").parse("2021-02-20 13:00:00:00");

        validEventRequestDto.setDateFrom(dateFrom);
        validEventRequestDto.setDateTo(dateTo);
        validEventRequestDto.setCity("TestCity");
        validEventRequestDto.setNumber(433);
        validEventRequestDto.setStreet("TestStreet");
        validEventRequestDto.setPostal("23-203");
        validEventRequestDto.setDiscipline("TestDiscipline");
        validEventRequestDto.setPeople(12);

        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");

        validEventRequestDto.setGenders(genders);
        validEventRequestDto.setMinAge(12);
        validEventRequestDto.setMaxAge(43);
        validEventRequestDto.setCost(12);
        validEventRequestDto.setDescription("This is test description");
        validEventRequestDto.setAuthor("test_username_one");



        return validEventRequestDto;


    }

    public static CityRequestDto getValidCityRequestDto(){
        validCityRequestDto.setCity("TestCity");
        return validCityRequestDto;
    }





}
