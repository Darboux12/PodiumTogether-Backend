package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.DisciplineAddControllerRequest;
import com.podium.controller.dto.response.DisciplineControllerResponse;
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

    public void add(DisciplineAddControllerRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.addDiscipline)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<DisciplineControllerResponse> findAll(HttpStatus status){

        DisciplineControllerResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllDiscipline)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) DisciplineControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public DisciplineControllerResponse findByName(String disciplineName, HttpStatus status){

        if(status == HttpStatus.OK)

        return  given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("discipline",disciplineName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findByDisciplineName)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(DisciplineControllerResponse.class);

        else given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("discipline",disciplineName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findByDisciplineName)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

        return null;

    }

    public boolean existDisciplineByName(String disciplineName, HttpStatus status){

        return

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("discipline",disciplineName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existDisciplineByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(boolean.class);

    }

    public void deleteDisciplineByName(String disciplineName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("discipline",disciplineName)
                .delete(PodiumPath.server + PodiumEndpoint.deleteDisciplineByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }
}
