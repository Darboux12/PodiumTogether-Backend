package com.podium.api;

import com.podium.helper.*;
import com.podium.model.entity.User;
import com.podium.model.request.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.assertion.Assertion;
import io.restassured.parsing.Parser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.text.ParseException;

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
    public void T01_signUpValidUser_ShouldReturnStatus_200() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestOne)
                .when()
                .post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T02_getSignedUpUserByUsername__ShouldReturnStatus_200(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("username",signUpRequestOne.getUsername())
                .when()
                .get(Path.server + Endpoint.getUserByUsernameEndpoint)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T02_getSignedUpUserByUsername__ShouldReturn_ValidUser(){

        User user =

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("username",signUpRequestOne.getUsername())
                .when()
                .get(Path.server + Endpoint.getUserByUsernameEndpoint)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(User.class);

        Assert.assertEquals(signUpRequestOne.getUsername(),user.getUsername());
    }

    @Test
    public void T03_getSignedUpUserByUsernameNotExist__ShouldReturn_Status_200(){

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("username","NotExistingUserName")
                        .when()
                        .get(Path.server + Endpoint.getUserByUsernameEndpoint)
                        .then().assertThat()
                        .statusCode(200)
                        .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T04_tryToAddSameUser_ShouldReturnStatus_409() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(Constant.getValidSignUpRequestOne())
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T05_tryToAddUser_WithSameUsername_ShouldReturnMessage_409() throws ParseException {

        signUpRequestTwo.setUsername(signUpRequestOne.getUsername());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestTwo.setUsername(initialUsernameTwo);

    }

    @Test
    public void T06_tryToAddUser_WithSameUsername_ShouldReturnHeader_AlreadyExist_Username() throws ParseException {

        signUpRequestTwo.setUsername(signUpRequestOne.getUsername());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .header("Already-Exist",equalTo("Username"))
                .spec(TestSpecification.buildResponseSpec());

        signUpRequestTwo.setUsername(initialUsernameTwo);

    }

    @Test
    public void T07_tryToAddUser_WithSameEmail_ShouldReturnMessage_409() throws ParseException {

        signUpRequestTwo.setEmail(signUpRequestOne.getEmail());

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
    public void T08_tryToAddSameUser_ShouldReturnHeader_AlreadyExist_Email() throws ParseException {

        System.out.println("HEhe" + signUpRequestTwo.getUsername());

        signUpRequestTwo.setEmail(signUpRequestOne.getEmail());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .header("Already-Exist",equalTo("Email"))
                .spec(TestSpecification.buildResponseSpec());;

        signUpRequestTwo.setEmail(initialEmailTwo);

    }

    @Test
    public void T09_deleteValidUser_ShouldReturnStatus_200() throws ParseException {

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username",Constant.getValidSignUpRequestOne().getUsername())
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec());

    }

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
    public void T11_tryToAddUser_WithoutUsername_ShouldReturnHeader_EmptyValue_Username() throws ParseException {

        signUpRequestTwo.setUsername("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .header("Empty-Value",equalTo("Username"))
                .spec(TestSpecification.buildResponseSpec());;

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
    public void T13_tryToAddUser_WithoutEmail_ShouldReturnHeader_EmptyValue_Email() throws ParseException {

        signUpRequestTwo.setEmail("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .header("Empty-Value",equalTo("Email"))
                .spec(TestSpecification.buildResponseSpec());;

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
    public void T15_tryToAddUser_WithToLongUsername_ShouldReturnHeader_LengthError() throws ParseException {

        signUpRequestTwo.setUsername("ThisUsernameIsExtremelyDefinitelyToLong");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .header("Length-Error",equalTo("Username"))
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
    public void T17_tryToAddUser_WithWrongCountry_ShouldReturnHeader_ImpossibleValue(){

        signUpRequestTwo.setCountry("NotExistingCountryName");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(signUpRequestTwo)
                .when().post(Path.server + Endpoint.addUserEndpoint)
                .then().assertThat()
                .header("Impossible-Value",equalTo("Country"))
                .spec(TestSpecification.buildResponseSpec());

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



}
