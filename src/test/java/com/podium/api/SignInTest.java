package com.podium.api;


import com.podium.logger.TestLogger;
import com.podium.model.dto.request.JwtRequestDto;
import com.podium.validator.UserValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;
import java.text.ParseException;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class SignInTest {

    private static JwtRequestDto request;
    private static String valueHolder;

     SignInTest(){}

    @BeforeAll
     static void beforeClass() throws ParseException {

        TestLogger.setUp();
        request = new JwtRequestDto("johndoe","johndoe123");
    }

    @Test
    void T01_Sign_In_User_Should__Return__Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.OK);

    }

    @Test
    void T02_Sign_In_Empty_Username_Should__Return__Status_CONFLICT() throws ParseException {

        valueHolder = request.getUsername();
        request.setUsername("");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.CONFLICT);

        request.setUsername(valueHolder);

    }

    @Test
    void T03_SignIn_Empty_Password_Should__Return__Status_CONFLICT() throws ParseException {

        valueHolder = request.getPassword();
        request.setPassword("");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.CONFLICT);

        request.setPassword(valueHolder);

    }

    @Test
    void T04_SignIn_Wrong_Password_Should_Return_Status_BADREQUEST() throws ParseException {

        valueHolder = request.getPassword();
        request.setPassword("WRONG PASSWORD");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.BAD_REQUEST);

        request.setPassword(valueHolder);

    }

    @Test
    void T05_SignIn_Wrong_Username_Should_Return_Status_UNAUTHORIZED() throws ParseException {

        valueHolder = request.getUsername();
        request.setUsername("WRONG USERNAME");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.UNAUTHORIZED);

        request.setUsername(valueHolder);

    }

    @Test
    void T06_SignIn_Wrong_Password_And_Username_Should_Return_Status_UNAUTHORIZED() throws ParseException {

        valueHolder = request.getUsername();
        request.setUsername("WRONG USERNAME");

        String passwordHolder = request.getPassword();
        request.setPassword("WRONG PASSWORD");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.UNAUTHORIZED);

        request.setUsername(valueHolder);
        request.setPassword(passwordHolder);

    }

}
