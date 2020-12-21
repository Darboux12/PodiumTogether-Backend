package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.GenderAddControllerRequest;
import com.podium.controller.dto.response.GenderControllerResponse;
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

    public void add(GenderAddControllerRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addGender)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<GenderControllerResponse> findAll(HttpStatus status){

        GenderControllerResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllGender)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) GenderControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public GenderControllerResponse findByName(String genderName, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("name",genderName)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findGenderByName)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(GenderControllerResponse.class);

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
