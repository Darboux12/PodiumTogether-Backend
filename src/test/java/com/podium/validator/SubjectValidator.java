package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.SubjectAddRequest;
import com.podium.controller.dto.response.ContactResponse;
import com.podium.controller.dto.response.SubjectResponse;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SubjectValidator {

    private static SubjectValidator instance;

    private SubjectValidator() {}

    public static SubjectValidator getInstance() {
        if(instance == null) {
            instance = new SubjectValidator();
        }
        return instance;
    }

    public void add(SubjectAddRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.addSubject)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<SubjectResponse> findAll(){

        SubjectResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllContact)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactResponse[].class);

        return Arrays.asList(dtos);

    }

    public SubjectResponse findByName(String subjectName, HttpStatus status){

        return

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .pathParam("name",subjectName)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findSubjectByName)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(SubjectResponse.class);

    }

    public void existCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",cityName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",cityName)
                .delete(PodiumPath.server + PodiumEndpoint.deleteCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
