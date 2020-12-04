package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.dto.response.SubjectResponseDto;
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

    public void add(SubjectRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(Path.server + PodiumEndpoint.addSubject)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<SubjectResponseDto> findAll(){

        SubjectResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + PodiumEndpoint.findAllContact)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type)ContactResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public SubjectResponseDto findByName(String subjectName, HttpStatus status){

        return

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .pathParam("name",subjectName)
                        .when()
                        .get(Path.server + PodiumEndpoint.findSubjectByName)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(SubjectResponseDto.class);

    }

    public void existCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",cityName)
                .when()
                .get(Path.server + PodiumEndpoint.existCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",cityName)
                .delete(Path.server + PodiumEndpoint.deleteCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
