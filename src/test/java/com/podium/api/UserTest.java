package com.podium.api;

import com.podium.helper.*;
import com.podium.model.request.SignUpRequest;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.text.ParseException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    private static  SignUpRequest signUpRequestOne;
    private static SignUpRequest signUpRequestTwo;
    private static String initialUsernameTwo;
    private static String initialEmailTwo;

    public UserTest(){}

    @BeforeClass
    public static void beforeClass() throws ParseException {

        TestLogger.setUp();
        signUpRequestOne = Constant.getValidSignUpRequestOne();
        signUpRequestTwo = Constant.getValidSignUpRequestTwo();
        initialUsernameTwo = Constant.getValidSignUpRequestTwo().getUsername();
        initialEmailTwo = Constant.getValidSignUpRequestTwo().getEmail();

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
    public void T02_tryToAddSameUser_ShouldReturnStatus_409() throws ParseException {

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
    public void T03_tryToAddUser_WithSameUsername_ShouldReturnMessage_409() throws ParseException {

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
    public void T04_tryToAddUser_WithSameUsername_ShouldReturnHeader_AlreadyExist_Username() throws ParseException {

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
    public void T05_tryToAddUser_WithSameEmail_ShouldReturnMessage_409() throws ParseException {

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
    public void T06_tryToAddSameUser_ShouldReturnHeader_AlreadyExist_Email() throws ParseException {

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
    public void T07_deleteValidUser_ShouldReturnStatus_200() throws ParseException {

        given().spec(TestSpecification.buildRequestSpec())
                .pathParam("username",Constant.getValidSignUpRequestOne().getUsername())
                .when()
                .delete(Path.server + Endpoint.deleteUserEndpoint)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T08_tryToAddUser_WithoutUsername_ShouldReturnStatus_409() throws ParseException {

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
    public void T09_tryToAddUser_WithoutUsername_ShouldReturnHeader_EmptyValue_Username() throws ParseException {

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
    public void T10_tryToAddUser_WithoutEmail_ShouldReturnStatus_409() throws ParseException {

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
    public void T11_tryToAddUser_WithoutEmail_ShouldReturnHeader_EmptyValue_Email() throws ParseException {

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
    public void T12_tryToAddUser_WithToLongUsername_ShouldReturnStatus_409() throws ParseException {

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
    public void T13_tryToAddUser_WithToLongUsername_ShouldReturnHeader_LengthError() throws ParseException {

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
}
