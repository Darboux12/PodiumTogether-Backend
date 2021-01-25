package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.ProfileUpdateControllerRequest;
import com.podium.controller.dto.response.JwtControllerResponse;
import com.podium.controller.dto.response.UserControllerResponse;
import com.podium.specification.TestSpecification;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.controller.dto.request.SignUpControllerRequest;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserValidator {

    private static UserValidator instance;

    private UserValidator() {}

    public static UserValidator getInstance() {
        if(instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    public void signUp(SignUpControllerRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.addUser)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public JwtControllerResponse signIn(JwtControllerRequest requestDto, HttpStatus status){

        if(status.value() == 200)

        return

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.authenticate)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(JwtControllerResponse.class);

        else

            given()
                    .spec(TestSpecification.buildRequestSpec())
                    .contentType(ContentType.JSON)
                    .body(requestDto)
                    .when()
                    .post(PodiumPath.server + PodiumEndpoint.authenticate)
                    .then().assertThat()
                    .statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec());

        return null;

    }

    public UserControllerResponse findUserByUsername(String username,String token, HttpStatus status){

        if(status == HttpStatus.OK)

            return
            given()
                    .spec(TestSpecification.buildRequestSpec())
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .pathParam("username", username)
                    .when()
                    .get(PodiumPath.server + PodiumEndpoint.findUserByUsername)
                    .then().assertThat()
                    .statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec())
                    .extract().as(UserControllerResponse.class);

        else

            given()
                    .spec(TestSpecification.buildRequestSpec())
                    .contentType(ContentType.JSON)
                    .pathParam("username",username)
                    .when()
                    .get(PodiumPath.server + PodiumEndpoint.findUserByUsername)
                    .then().assertThat()
                    .statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec());

        return null;


    }

    public void deleteUserByUsername(String username,String token, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(PodiumPath.server + PodiumEndpoint.deleteUser)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<UserControllerResponse> findAll(String token, HttpStatus status){

        UserControllerResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findAllUsers)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) UserControllerResponse[].class);

        return Arrays.asList(dtos);


    }

    public void updateUser(ProfileUpdateControllerRequest requestDto, HttpStatus status ){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.updateUserProfile)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
