package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.model.dto.request.ProfileUpdateRequestDto;
import com.podium.specification.TestSpecification;
import com.podium.model.dto.request.JwtRequestDto;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.dto.response.UserResponseDto;
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

    public void signUp(SignUpRequestDto requestDto, HttpStatus status){

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

    public void signIn(JwtRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.authenticate)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public UserResponseDto findUserByUsername(String username, HttpStatus status){

        if(status == HttpStatus.OK)

            return
            given()
                    .spec(TestSpecification.buildRequestSpec())
                    .contentType(ContentType.JSON)
                    .pathParam("username", username)
                    .when()
                    .get(PodiumPath.server + PodiumEndpoint.findUserByUsername)
                    .then().assertThat()
                    .statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec())
                    .extract().as(UserResponseDto.class);

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

    public void deleteUserByUsername(String username, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .when()
                .delete(PodiumPath.server + PodiumEndpoint.deleteUser)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<UserResponseDto> findAll(){

        UserResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findAllUsers)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) UserResponseDto[].class);

        return Arrays.asList(dtos);


    }

    public void updateUser(ProfileUpdateRequestDto requestDto, HttpStatus status ){

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
