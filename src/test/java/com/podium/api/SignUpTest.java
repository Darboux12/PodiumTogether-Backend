package com.podium.api;

import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.constant.PodiumLimits;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpTest {

    private static SignUpRequestDto signUpRequestDtoOne;
    private static SignUpRequestDto signUpRequestDtoTwo;
    private static String initialUsernameTwo;
    private static String initialEmailTwo;
    private static String valueHolder;

    public SignUpTest(){}

    @BeforeClass
    public static void beforeClass() throws ParseException {

        TestLogger.setUp();

        signUpRequestDtoOne = new SignUpRequestDto(
                "TEST USERNAME_ONE",
                "TEST_MAIL_ONE@gmail.com",
                "TEST PASSWORD ONE",
                "POLAND",
                new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
        );

        signUpRequestDtoTwo = new SignUpRequestDto(
                "TEST USERNAME_TWO",
                "TEST_MAIL_TWO@gmail.com",
                "TEST PASSWORD TWO",
                "POLAND",
                new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
        );

        initialUsernameTwo = signUpRequestDtoTwo.getUsername();
        initialEmailTwo =  signUpRequestDtoTwo.getEmail();

    }

    @Test
    public void T01_Sign_Up_Valid_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.OK);

    }

    @Test
    public void T02_Find_Signed_Up_User_By_Username_Should_Return_Status_OK_And_DTO(){

        UserResponseDto responseDto =

        UserValidator
                .getInstance()
                .findUserByUsername(signUpRequestDtoOne.getUsername(),HttpStatus.OK);

        Assert.assertEquals(signUpRequestDtoOne.getUsername(),responseDto.getUsername());
    }

    @Test
    public void T03_Find_All_User_Should_Return_Status_OK_And_Iterable_DTO(){

        boolean isPresent = UserValidator
                .getInstance()
                .findAll()
                .stream()
                .map(UserResponseDto::getUsername)
                .anyMatch(signUpRequestDtoOne.getUsername()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T04_Find_User_By_Username_Not_Exist_Should_Return_Status_NOTFOUND(){

        UserValidator
                .getInstance()
                .findUserByUsername("NonExistentUsername",HttpStatus.NOT_FOUND);

    }

    @Test
    public void T05_Sign_Up_Same_User_Should_Return_Status_CONFLICT() throws ParseException {

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

    }

    @Test
    public void T06_Sign_Up_User_With_Same_Username_Should_Return_Status_CONFLICT() throws ParseException {

        signUpRequestDtoTwo.setUsername(signUpRequestDtoOne.getUsername());

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoTwo.setUsername(initialUsernameTwo);

    }

    @Test
    public void T07_Sign_Up_User_Existing_Email_Should_Return_Status_CONFLICT() throws ParseException {

        signUpRequestDtoTwo.setEmail(signUpRequestDtoOne.getEmail());

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoTwo.setEmail(initialEmailTwo);
    }

    @Test
    public void T08_Sign_Up_User_Empty_Username_Should_Return_Status_CONFLICT() throws ParseException {

        valueHolder = signUpRequestDtoOne.getUsername();
        signUpRequestDtoOne.setUsername("");

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setUsername(valueHolder);
    }

    @Test
    public void T09_Sign_Up_ToShortUsername_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minUsernameLength - 1);

        valueHolder = signUpRequestDtoOne.getUsername();
        signUpRequestDtoOne.setUsername(toShort);

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setUsername(valueHolder);
    }

    @Test
    public void T10_SignUpUserToLongUsername_Should_Return_Status_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxUsernameLength + 1);

        valueHolder = signUpRequestDtoOne.getUsername();
        signUpRequestDtoOne.setUsername(toLong);

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setUsername(valueHolder);
    }

    @Test
    public void T11_signUpUserEmptyEmail_Should_Return_Status_CONFLICT() throws ParseException {


        valueHolder = signUpRequestDtoOne.getEmail();
        signUpRequestDtoOne.setEmail("");

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setEmail(valueHolder);
    }

    @Test
    public void T12_signUpUserInvalidEmail_Should_Return_Status_CONFLICT() throws ParseException {

        valueHolder = signUpRequestDtoOne.getEmail();
        signUpRequestDtoOne.setEmail("invalidEmail@");

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setEmail(valueHolder);
    }

    @Test
    public void T13_signUpUserToLongEmail_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxEmailLength + 1);

        valueHolder = signUpRequestDtoOne.getEmail();
        signUpRequestDtoOne.setEmail(toLong);

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setEmail(valueHolder);
    }

    @Test
    public void T14_signUpUserToShortPassword_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minPasswordLength - 1);

        valueHolder = signUpRequestDtoOne.getPassword();
        signUpRequestDtoOne.setPassword(toShort);

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setPassword(valueHolder);
    }

    @Test
    public void T15_signUpUserToShortPassword_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxPasswordLength + 1);

        valueHolder = signUpRequestDtoOne.getPassword();
        signUpRequestDtoOne.setPassword(toLong);

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setPassword(valueHolder);
    }

    @Test
    public void T16_signUpUserEmptyCountry_Should_Return_Status_CONFLICT() throws ParseException {

        valueHolder = signUpRequestDtoOne.getCountry();
        signUpRequestDtoOne.setCountry("");

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setCountry(valueHolder);
    }

    @Test
    public void T17_signUpUserBirthdayInFuture_Should_Return_Status_CONFLICT() throws ParseException {

        Date tmpDate = signUpRequestDtoOne.getBirthday();
        signUpRequestDtoOne.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2100"));

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne,HttpStatus.CONFLICT);

        signUpRequestDtoOne.setBirthday(tmpDate);
    }

    @Test
    public void T18_Delete_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .deleteUserByUsername(signUpRequestDtoOne.getUsername(),HttpStatus.OK);

    }

    @Test
    public void T19_Delete_Same_User_Should_Return_Status_NOTFOUND() throws ParseException{

        UserValidator
                .getInstance()
                .deleteUserByUsername(signUpRequestDtoOne.getUsername(),HttpStatus.NOT_FOUND);
    }

}
