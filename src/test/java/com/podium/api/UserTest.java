package com.podium.api;

import com.podium.helper.*;
import com.podium.model.entity.User;
import com.podium.model.request.JwtRequest;
import com.podium.model.request.SignUpRequest;
import com.podium.validation.validators.PodiumLimits;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.assertion.Assertion;
import io.restassured.parsing.Parser;
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

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    private static  SignUpRequest signUpRequestOne;
    private static SignUpRequest signUpRequestTwo;
    private static String initialUsernameTwo;
    private static String initialEmailTwo;
    private static String initialCountryTwo;

    private static String valueHolder;

    public UserTest(){}

    @BeforeClass
    public static void beforeClass() throws ParseException {

        TestLogger.setUp();
        signUpRequestOne = Constant.getValidSignUpRequestOne();
        signUpRequestTwo = Constant.getValidSignUpRequestTwo();
        initialUsernameTwo = Constant.getValidSignUpRequestTwo().getUsername();
        initialEmailTwo = Constant.getValidSignUpRequestTwo().getEmail();
        initialCountryTwo = Constant.getValidSignUpRequestTwo().getCountry();

    }

    @Test
    public void T01_signUpValidUser_ShouldReturnStatus_OK() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T02_signInUser_ShouldReturnStatus_OK() throws ParseException {

        JwtRequest request =
                new JwtRequest(signUpRequestOne.getUsername(),signUpRequestOne.getPassword());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_signInUserEmptyUsername_ShouldReturnStatus_CONFLICT() throws ParseException {

        JwtRequest request =
                new JwtRequest("",signUpRequestOne.getPassword());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T04_signInUserEmptyPassword_ShouldReturnStatus_CONFLICT() throws ParseException {

        JwtRequest request =
                new JwtRequest(signUpRequestOne.getUsername(),"");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T05_signInUserWrongPassword_ShouldReturnStatus_BADREQUEST() throws ParseException {

        JwtRequest request =
                new JwtRequest(signUpRequestOne.getUsername(),"WrongPassword");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T06_signInUserWrongUsername_ShouldReturnStatus_UNAUTHORIZED() throws ParseException {

        JwtRequest request =
                new JwtRequest("WrongUsername",signUpRequestOne.getPassword());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T07_signInUserWrongPasswordAndUsername_ShouldReturnStatus_UNAUTHORIZED() throws ParseException {

        JwtRequest request =
                new JwtRequest("WrongUsername","WrongPassword");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Path.server + Endpoint.authenticateEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T08_getSignedUpUserByUsername__ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("username",signUpRequestOne.getUsername())
                .when()
                .get(Path.server + Endpoint.getUserByUsernameEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T09_getSignedUpUserByUsername__ShouldReturn_ValidUser(){

        User user =

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("username",signUpRequestOne.getUsername())
                .when()
                .get(Path.server + Endpoint.getUserByUsernameEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(User.class);

        Assert.assertEquals(signUpRequestOne.getUsername(),user.getUsername());
    }

    @Test
    public void T10_getSignedUpUserByUsernameNotExist__ShouldReturnStatus_NOTFOUND(){

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("username","NotExistingUserName")
                        .when()
                        .get(Path.server + Endpoint.getUserByUsernameEndpoint)
                        .then().assertThat()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T11_tryToAddSameUser_ShouldReturnStatus_CONFLICT() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(Constant.getValidSignUpRequestOne())
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T12_tryToAddUser_WithSameUsername_ShouldReturnStatus_CONFLICT() throws ParseException {

        signUpRequestTwo.setUsername(signUpRequestOne.getUsername());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestTwo.setUsername(initialUsernameTwo);

    }

    @Test
    public void T13_tryToAddUser_WithSameEmail_ShouldReturnStatus_CONFLICT() throws ParseException {

        signUpRequestTwo.setEmail(signUpRequestOne.getEmail());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestTwo.setEmail(initialEmailTwo);
    }

    @Test
    public void T14_deleteValidUser_ShouldReturnStatus_OK() throws ParseException {

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username",Constant.getValidSignUpRequestOne().getUsername())
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T15_signUpUserEmptyUsername_ShouldReturnStatus_CONFLICT() throws ParseException {

        valueHolder = signUpRequestOne.getUsername();
        signUpRequestOne.setUsername("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setUsername(valueHolder);
    }

    @Test
    public void T16_signUpUserToShortUsername_PodiumLimits_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minUsernameLength - 1);

        valueHolder = signUpRequestOne.getUsername();
        signUpRequestOne.setUsername(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setUsername(valueHolder);
    }

    @Test
    public void T17_signUpUserToLongUsername_PodiumLimits_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxUsernameLength + 1);

        valueHolder = signUpRequestOne.getUsername();
        signUpRequestOne.setUsername(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setUsername(valueHolder);
    }

    @Test
    public void T18_signUpUserEmptyEmail_ShouldReturnStatus_CONFLICT() throws ParseException {


        valueHolder = signUpRequestOne.getEmail();
        signUpRequestOne.setEmail("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setEmail(valueHolder);
    }

    @Test
    public void T19_signUpUserInvalidEmail_ShouldReturnStatus_CONFLICT() throws ParseException {

        valueHolder = signUpRequestOne.getEmail();
        signUpRequestOne.setEmail("invalidEmail@");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setEmail(valueHolder);
    }

    @Test
    public void T20_signUpUserToLongEmail_PodiumLimits_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxEmailLength + 1);

        valueHolder = signUpRequestOne.getEmail();
        signUpRequestOne.setEmail(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setEmail(valueHolder);
    }

    @Test
    public void T21_signUpUserToShortPassword_PodiumLimits_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minPasswordLength - 1);

        valueHolder = signUpRequestOne.getPassword();
        signUpRequestOne.setPassword(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setPassword(valueHolder);
    }

    @Test
    public void T22_signUpUserToShortPassword_PodiumLimits_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxPasswordLength + 1);

        valueHolder = signUpRequestOne.getPassword();
        signUpRequestOne.setPassword(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setPassword(valueHolder);
    }

    @Test
    public void T23_signUpUserEmptyCountry_ShouldReturnStatus_CONFLICT() throws ParseException {

        valueHolder = signUpRequestOne.getCountry();
        signUpRequestOne.setCountry("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setCountry(valueHolder);
    }

    @Test
    public void T24_signUpUserBirthdayInFuture_ShouldReturnStatus_CONFLICT() throws ParseException {

        Date tmpDate = signUpRequestOne.getBirthday();
        signUpRequestOne.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2100"));

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setBirthday(tmpDate);
    }








    /*
    @Test
    public void T10_tryToAddUser_WithoutUsername_ShouldReturnStatus_409() throws ParseException {

        signUpRequestTwo.setUsername("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409);

        signUpRequestTwo.setUsername(initialUsernameTwo);



    }


    @Test
    public void T12_tryToAddUser_WithoutEmail_ShouldReturnStatus_409() throws ParseException {

        signUpRequestTwo.setEmail("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestTwo.setEmail(initialEmailTwo);
    }



    @Test
    public void T14_tryToAddUser_WithToLongUsername_ShouldReturnStatus_409() throws ParseException {

        signUpRequestTwo.setUsername("ThisUsernameIsExtremelyDefinitelyToLong");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());;

        signUpRequestTwo.setUsername(initialUsernameTwo);

    }



    @Test
    public void T16_tryToAddUser_WithWrongCountry_ShouldReturnStatus_409() throws ParseException {

        signUpRequestTwo.setCountry("NotExistingCountryName");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());;

        signUpRequestTwo.setCountry(initialCountryTwo);
    }



    @Test
    public void T17_tryToDeleteUser_NotExist_ShouldReturnStatus_404(){

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username","NotExistingUsername")
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(404)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T18_getAllSUsers_ShouldReturnStatus_200(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .get(Path.server + Endpoint.findAllUsers)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T19_getAllSUsers_ShouldReturnIterable_Users(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllUsers)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(User[].class);
    }

    @Test
    public void T20_deleteNotExistingUser_ShouldReturnStatus_404() throws ParseException {

        signUpRequestTwo.setUsername("NotExistingUserName");

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username",signUpRequestTwo.getUsername())
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(404)
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestOne.setUsername(initialUsernameTwo);

    }

    @Test
    public void T21_signInWithoutUsername_ShouldReturnStatus_409() throws ParseException {

        String username = "";
        String password = "testPassword";

        JwtRequest jwtRequest = new JwtRequest();
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
