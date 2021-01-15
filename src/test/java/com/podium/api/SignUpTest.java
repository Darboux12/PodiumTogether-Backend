package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.SignUpControllerRequest;
import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.response.UserControllerResponse;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class SignUpTest {

    private static SignUpControllerRequest signUpControllerRequestOne;
    private static SignUpControllerRequest signUpControllerRequestTwo;
    private static String initialUsernameTwo;
    private static String initialEmailTwo;
    private static String valueHolder;

     SignUpTest(){}

    @BeforeAll
    static void beforeClass() throws ParseException {

        TestLogger.setUp();

        signUpControllerRequestOne = new SignUpControllerRequest(
                "TEST USERNAME_ONE",
                "TEST_MAIL_ONE@gmail.com",
                "TEST PASSWORD ONE",
                "POLAND",
                new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
        );

        signUpControllerRequestTwo = new SignUpControllerRequest(
                "TEST USERNAME_TWO",
                "TEST_MAIL_TWO@gmail.com",
                "TEST PASSWORD TWO",
                "GERMANY",
                new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
        );

        initialUsernameTwo = signUpControllerRequestTwo.getUsername();
        initialEmailTwo =  signUpControllerRequestTwo.getEmail();

    }

    @Test
    void T01_Sign_Up_Valid_User_Should_Return_Status_OK() {

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.OK);

    }

    @Test
    void T02_Find_Signed_Up_User_By_Username_Should_Return_Status_OK_And_DTO(){

        UserControllerResponse responseDto =

        UserValidator
                .getInstance()
                .findUserByUsername(signUpControllerRequestOne.getUsername(),HttpStatus.OK);

        Assertions.assertEquals(signUpControllerRequestOne.getUsername(),responseDto.getUsername());
    }

    @Test
    void T03_Find_All_User_Should_Return_Status_OK_And_Iterable_DTO(){

        boolean isPresent = UserValidator
                .getInstance()
                .findAll()
                .stream()
                .map(UserControllerResponse::getUsername)
                .anyMatch(signUpControllerRequestOne.getUsername()::equals);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T04_Find_User_By_Username_Not_Exist_Should_Return_Status_NOTFOUND(){

        UserValidator
                .getInstance()
                .findUserByUsername("NonExistentUsername",HttpStatus.NOT_FOUND);

    }

    @Test
    void T05_Sign_Up_Same_User_Should_Return_Status_CONFLICT() throws ParseException {

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

    }

    @Test
    void T06_Sign_Up_User_With_Same_Username_Should_Return_Status_CONFLICT() throws ParseException {

        signUpControllerRequestTwo.setUsername(signUpControllerRequestOne.getUsername());

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestTwo.setUsername(initialUsernameTwo);

    }

    @Test
    void T07_Sign_Up_User_Existing_Email_Should_Return_Status_CONFLICT() throws ParseException {

        signUpControllerRequestTwo.setEmail(signUpControllerRequestOne.getEmail());

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestTwo.setEmail(initialEmailTwo);
    }

    @Test
    void T08_Sign_Up_User_Empty_Username_Should_Return_Status_CONFLICT() throws ParseException {

        valueHolder = signUpControllerRequestOne.getUsername();
        signUpControllerRequestOne.setUsername("");

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setUsername(valueHolder);
    }

    @Test
    void T09_Sign_Up_ToShortUsername_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minUsernameLength - 1);

        valueHolder = signUpControllerRequestOne.getUsername();
        signUpControllerRequestOne.setUsername(toShort);

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setUsername(valueHolder);
    }

    @Test
    void T10_SignUpUserToLongUsername_Should_Return_Status_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxUsernameLength + 1);

        valueHolder = signUpControllerRequestOne.getUsername();
        signUpControllerRequestOne.setUsername(toLong);

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setUsername(valueHolder);
    }

    @Test
    void T11_signUpUserEmptyEmail_Should_Return_Status_CONFLICT() throws ParseException {


        valueHolder = signUpControllerRequestOne.getEmail();
        signUpControllerRequestOne.setEmail("");

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setEmail(valueHolder);
    }

    @Test
    void T12_signUpUserInvalidEmail_Should_Return_Status_CONFLICT() throws ParseException {

        valueHolder = signUpControllerRequestOne.getEmail();
        signUpControllerRequestOne.setEmail("invalidEmail@");

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setEmail(valueHolder);
    }

    @Test
    void T13_signUpUserToLongEmail_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxEmailLength + 1);

        valueHolder = signUpControllerRequestOne.getEmail();
        signUpControllerRequestOne.setEmail(toLong);

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setEmail(valueHolder);
    }

    @Test
    void T14_signUpUserToShortPassword_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minPasswordLength - 1);

        valueHolder = signUpControllerRequestOne.getPassword();
        signUpControllerRequestOne.setPassword(toShort);

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setPassword(valueHolder);
    }

    @Test
    void T15_signUpUserToShortPassword_PodiumLimits_Should_Return_Status_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxPasswordLength + 1);

        valueHolder = signUpControllerRequestOne.getPassword();
        signUpControllerRequestOne.setPassword(toLong);

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setPassword(valueHolder);
    }

    @Test
    void T16_signUpUserEmptyCountry_Should_Return_Status_CONFLICT() throws ParseException {

        valueHolder = signUpControllerRequestOne.getCountry();
        signUpControllerRequestOne.setCountry("");

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setCountry(valueHolder);
    }

    @Test
    void T17_signUpUserBirthdayInFuture_Should_Return_Status_CONFLICT() throws ParseException {

        Date tmpDate = signUpControllerRequestOne.getBirthday();
        signUpControllerRequestOne.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2100"));

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne,HttpStatus.CONFLICT);

        signUpControllerRequestOne.setBirthday(tmpDate);
    }

    @Test
    void T18_Delete_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .deleteUserByUsername(signUpControllerRequestOne.getUsername(),HttpStatus.OK);

    }

    @Test
    void T19_Delete_Same_User_Should_Return_Status_NOTFOUND() throws ParseException{

        UserValidator
                .getInstance()
                .deleteUserByUsername(signUpControllerRequestOne.getUsername(),HttpStatus.NOT_FOUND);
    }

}
