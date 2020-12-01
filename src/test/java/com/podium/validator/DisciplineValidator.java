package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.discipline.DisciplineRequestDto;
import com.podium.model.dto.response.discipline.DisciplineResponseDto;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class DisciplineValidator {


    private static DisciplineValidator instance;

    private DisciplineValidator() {}

    public static DisciplineValidator getInstance() {
        if(instance == null) {
            instance = new DisciplineValidator();
        }
        return instance;
    }

    public void add(DisciplineRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(Path.server + PodiumEndpoint.addDiscipline)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<DisciplineResponseDto> findAll(HttpStatus status){

        DisciplineResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + PodiumEndpoint.findAllDiscipline)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) DisciplineResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public DisciplineResponseDto findByName(String disciplineName, HttpStatus status){

        if(status == HttpStatus.OK)

        return  given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("discipline",disciplineName)
                .when()
                .get(Path.server + PodiumEndpoint.findByDisciplineName)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(DisciplineResponseDto.class);

        else given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("discipline",disciplineName)
                .when()
                .get(Path.server + PodiumEndpoint.findByDisciplineName)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

        return null;

    }

    public void existDisciplineByName(String disciplineName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("discipline",disciplineName)
                .when()
                .get(Path.server + PodiumEndpoint.existDisciplineByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteDisciplineByName(String disciplineName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("discipline",disciplineName)
                .delete(Path.server + PodiumEndpoint.deleteDisciplineByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }
}
