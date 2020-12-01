package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.user.ProfileUpdateRequestDto;
import com.podium.specification.TestSpecification;
import com.podium.model.dto.request.authentication.JwtRequestDto;
import com.podium.model.dto.request.user.SignUpRequestDto;
import com.podium.model.dto.response.user.UserResponseDto;
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
                .post(Path.server + PodiumEndpoint.addUser)
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
                .post(Path.server + PodiumEndpoint.authenticate)
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
                    .get(Path.server + PodiumEndpoint.findUserByUsername)
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
                    .get(Path.server + PodiumEndpoint.findUserByUsername)
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
                .delete(Path.server + PodiumEndpoint.deleteUser)
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
                        .get(Path.server + PodiumEndpoint.findAllUsers)
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
                .post(Path.server + PodiumEndpoint.updateUserProfile)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
