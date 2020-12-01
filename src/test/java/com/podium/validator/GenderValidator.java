package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.gender.GenderRequestDto;
import com.podium.model.dto.response.gender.GenderResponseDto;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GenderValidator {

    private static GenderValidator instance;

    private GenderValidator() {}

    public static GenderValidator getInstance() {
        if(instance == null) {
            instance = new GenderValidator();
        }
        return instance;
    }

    public void add(GenderRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + PodiumEndpoint.addGender)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<GenderResponseDto> findAll(HttpStatus status){

        GenderResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + PodiumEndpoint.findAllGender)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) GenderResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public GenderResponseDto findByName(String genderName, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("name",genderName)
                        .when()
                        .get(Path.server + PodiumEndpoint.findGenderByName)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(GenderResponseDto.class);

    }

    public void existGenderByName(String genderName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",genderName)
                .when()
                .get(Path.server + PodiumEndpoint.existGenderByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteGenderByName(String genderName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",genderName)
                .delete(Path.server + PodiumEndpoint.deleteGenderByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());
    }
}
