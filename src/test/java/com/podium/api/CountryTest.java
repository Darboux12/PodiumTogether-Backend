package com.podium.api;

import com.podium.helper.*;
import com.podium.model.dto.response.CountryResponseDto;
import io.restassured.http.ContentType;
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
public class CountryTest {

    private static CountryRequestDto requestDto;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = Constant.getValidCountryRequestDto();
    }

    @Test
    public void T01_addValidCountry_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCountry)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_getAllCountry_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + Endpoint.findAllCountry)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_getAllCountry_ShouldReturnIterable_CountryResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllCountry)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(CountryResponseDto[].class);

    }

    @Test
    public void T04_deleteCreatedCountry_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getName())
                .delete(Path.server + Endpoint.deleteCountryByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T05_deleteCreatedCountryAgain_ShouldReturnStatus_NOTFOUND(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getName())
                .delete(Path.server + Endpoint.deleteCountryByName)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T06_addCountryEmptyName_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getName();
        requestDto.setName("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCountry)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setName(valueHolder);

    }

    @Test
    public void T07_addCountryEmptyId_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getCountryId();
        requestDto.setCountryId("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCountry)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setCountryId(valueHolder);

    }

    @Test
    public void T08_addCountryEmptyPrintableName_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getPrintableName();
        requestDto.setPrintableName("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCountry)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPrintableName(valueHolder);

    }

    @Test
    public void T09_addCountryEmptyIso3_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getIso3();
        requestDto.setIso3("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCountry)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setIso3(valueHolder);

    }

}
