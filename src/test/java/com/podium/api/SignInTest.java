package com.podium.api;


import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.JwtRequestDto;
import com.podium.validator.UserValidator;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import java.text.ParseException;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class SignInTest {

    private static String validPassword = "johndoe123";
    private static String validUsername = "johndoe";

    private static Stream<String> provideEmptyValuesForTests(){
        return Stream.of("", " ", "  ", "       ");
    }

    private static Stream<String> provideWrongPasswordsForTests(){
        return Stream.of(
                "WRONG",
                "WRONG PASSWORD ONE",
                "WRONG PASSWORD ONE WRONG PASSWORD TWO",
                "WRONG PASSWORD ONE WRONG PASSWORD TWO WRONG PASSWORD THREE",
                "WRONG PASSWORD ONE WRONG PASSWORD TWO WRONG PASSWORD THREE FOUR FIVE"
        );
    }

    private static Stream<String> provideWrongUsernameForTests(){
        return Stream.of(
                "WRONG",
                "WRONG USERNAME ONE",
                "WRONG USERNAME ONE WRONG USERNAME TWO",
                "WRONG USERNAME ONE WRONG USERNAME TWO WRONG USERNAME THREE",
                "WRONG USERNAME ONE WRONG USERNAME TWO WRONG USERNAME THREE FOUR FIVE"
        );
    }

    private static Stream<Arguments> provideWrongUsernameAndPasswordForTests(){
        return Stream.of(
                Arguments.of("WRONG","WRONG"),
                Arguments.of("WRONG USERNAME ONE","WRONG PASSWORD ONE"),
                Arguments.of("WRONG USERNAME TWO TWO TWO","WRONG PASSWORD TWO TWO")
        );
    }

    private static Stream<JwtRequestDto> provideValidSIgnInRequests(){

        return Stream.of(
                new JwtRequestDto("johndoe","johndoe123")

        );

    }

    SignInTest(){}

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    @ParameterizedTest
    @MethodSource("provideValidSIgnInRequests")
    void T01_Sign_In_User_Should__Return__Status_OK(JwtRequestDto request){
        UserValidator.getInstance().signIn(request,HttpStatus.OK);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T02_Sign_In_Empty_Username_Should_Return_Status_CONFLICT(String emptyUsername){

        UserValidator.getInstance()
                .signIn(new JwtRequestDto(emptyUsername,validPassword),HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T03_SignIn_Empty_Password_Should_Return_Status_CONFLICT(String emptyPassword) {
        UserValidator
                .getInstance()
                .signIn(new JwtRequestDto(validUsername,emptyPassword),HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideWrongPasswordsForTests")
    void T04_SignIn_Wrong_Password_Should_Return_Status_BADREQUEST(String wrongPassword){

        UserValidator
                .getInstance()
                .signIn(new JwtRequestDto(validUsername,wrongPassword),HttpStatus.BAD_REQUEST);

    }

    @ParameterizedTest
    @MethodSource("provideWrongUsernameForTests")
    void T05_SignIn_Wrong_Username_Should_Return_Status_UNAUTHORIZED(String wrongUsername){
        UserValidator
                .getInstance()
                .signIn(new JwtRequestDto(wrongUsername,validPassword),HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @MethodSource("provideWrongUsernameAndPasswordForTests")
    void T06_SignIn_Wrong_Password_And_Username_Should_Return_Status_UNAUTHORIZED(String wrongUsername, String wrongPassword){

        UserValidator
                .getInstance()
                .signIn(new JwtRequestDto(wrongUsername,wrongPassword),HttpStatus.UNAUTHORIZED);
    }

}
