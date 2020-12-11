package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.GenderAddRequest;
import com.podium.controller.dto.response.GenderResponse;
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

    public void add(GenderAddRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addGender)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<GenderResponse> findAll(HttpStatus status){

        GenderResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllGender)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) GenderResponse[].class);

        return Arrays.asList(dtos);

    }

    public GenderResponse findByName(String genderName, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("name",genderName)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findGenderByName)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(GenderResponse.class);

    }

    public boolean existGenderByName(String genderName, HttpStatus status){

        return

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",genderName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existGenderByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(boolean.class);

    }

    public void deleteGenderByName(String genderName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",genderName)
                .delete(PodiumPath.server + PodiumEndpoint.deleteGenderByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());
    }
}
