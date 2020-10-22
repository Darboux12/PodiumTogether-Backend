package com.podium.api;

import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.JwtRequestDto;
import com.podium.validator.UserValidator;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import java.text.ParseException;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInTest {

    private static JwtRequestDto request;
    private static String valueHolder;

    public SignInTest(){}

    @BeforeClass
    public static void beforeClass() throws ParseException {

        TestLogger.setUp();
        request = Constant.getSignInRequest();
    }

    @Test
    public void T01_Sign_In_User_Should__Return__Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.OK);

    }

    @Test
    public void T02_Sign_In_Empty_Username_Should__Return__Status_CONFLICT() throws ParseException {

        valueHolder = request.getUsername();
        request.setUsername("");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.CONFLICT);

        request.setUsername(valueHolder);

    }

    @Test
    public void T03_SignIn_Empty_Password_Should__Return__Status_CONFLICT() throws ParseException {

        valueHolder = request.getPassword();
        request.setPassword("");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.CONFLICT);

        request.setPassword(valueHolder);

    }

    @Test
    public void T04_SignIn_Wrong_Password_Should_Return_Status_BADREQUEST() throws ParseException {

        valueHolder = request.getPassword();
        request.setPassword("WRONG PASSWORD");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.BAD_REQUEST);

        request.setPassword(valueHolder);

    }

    @Test
    public void T05_SignIn_Wrong_Username_Should_Return_Status_UNAUTHORIZED() throws ParseException {

        valueHolder = request.getUsername();
        request.setUsername("WRONG USERNAME");

        UserValidator
                .getInstance()
                .signIn(request,HttpStatus.UNAUTHORIZED);

        request.setUsername(valueHolder);

    }

    @Test
    public void T06_SignIn_Wrong_Password_And_Username_Should_Return_Status_UNAUTHORIZED() throws ParseException {

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

    /*
    @Test
    public void T10_tryToAddUser_WithoutUsername_Should_Return_Status_409() throws ParseException {

        signUpRequestDtoTwo.setUsername("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestDtoTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409);

        signUpRequestDtoTwo.setUsername(initialUsernameTwo);



    }


    @Test
    public void T12_tryToAddUser_WithoutEmail_Should_Return_Status_409() throws ParseException {

        signUpRequestDtoTwo.setEmail("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestDtoTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestDtoTwo.setEmail(initialEmailTwo);
    }



    @Test
    public void T14_tryToAddUser_WithToLongUsername_Should_Return_Status_409() throws ParseException {

        signUpRequestDtoTwo.setUsername("ThisUsernameIsExtremelyDefinitelyToLong");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestDtoTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());;

        signUpRequestDtoTwo.setUsername(initialUsernameTwo);

    }



    @Test
    public void T16_tryToAddUser_WithWrongCountry_Should_Return_Status_409() throws ParseException {

        signUpRequestDtoTwo.setCountry("NotExistingCountryName");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestDtoTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());;

        signUpRequestDtoTwo.setCountry(initialCountryTwo);
    }



    @Test
    public void T17_tryToDeleteUser_NotExist_Should_Return_Status_404(){

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username","NotExistingUsername")
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(404)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T18_getAllSUsers_Should_Return_Status_200(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .get(Path.server + Endpoint.findAllUsers)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T19_getAllSUsers_Should_Return_Iterable_Users(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllUsers)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(User[].class);
    }

    @Test
    public void T20_deleteNotExistingUser_Should_Return_Status_404() throws ParseException {

        signUpRequestDtoTwo.setUsername("NotExistingUserName");

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username",signUpRequestDtoTwo.getUsername())
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(404)
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestDtoOne.setUsername(initialUsernameTwo);

    }

    @Test
    public void T21_signInWithoutUsername_Should_Return_Status_409() throws ParseException {

        String username = "";
        String password = "testPassword";

        JwtRequestDto jwtRequest = new JwtRequestDto();
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .with().body(jwtRequest)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat().statusCode(409)
                .spec(TestSpecification.buildResponseSpec());

    }

*/

}
