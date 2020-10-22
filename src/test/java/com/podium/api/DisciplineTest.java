package com.podium.api;

import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.specification.TestSpecification;
import com.podium.validation.validators.PodiumLimits;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisciplineTest {

   private static DisciplineRequestDto requestDto;
   private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = Constant.getValidDisciplineRequestDto();
    }

    @Test
    public void T01_addValidDiscipline_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addDiscipline)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_getAllDiscipline_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + Endpoint.findAllNews)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_getAllDiscipline_ShouldReturnIterable_DisciplineResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllDiscipline)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(DisciplineResponseDto[].class);

    }

    @Test
    public void T04_deleteCreatedDiscipline_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("discipline",requestDto.getDiscipline())
                .delete(Path.server + Endpoint.deleteDisciplineByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());


    }

    @Test
    public void T05_deleteCreatedDisciplineAgain_ShouldReturnStatus_NOTFOUND(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("discipline",requestDto.getDiscipline())
                .delete(Path.server + Endpoint.deleteDisciplineByName)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T06_addDisciplineEmptyName_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addDiscipline)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T07_addDisciplineToShortName_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addDiscipline)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

       requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T08_addDisciplineToLongName_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addDiscipline)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

       requestDto.setDiscipline(valueHolder);

    }



}
