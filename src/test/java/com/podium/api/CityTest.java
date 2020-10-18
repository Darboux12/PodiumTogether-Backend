package com.podium.api;

import com.podium.TestValidator.TestValidator;
import com.podium.helper.*;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
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
public class CityTest {

    private static CityRequestDto requestDto;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = Constant.getValidCityRequestDto();
    }

    @Test
    public void T01_addValidCity_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCity)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_addSameCityAgain_ShouldReturnStatus_CONFLICT(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCity)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_getAllCity_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + Endpoint.findAllCity)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(CityResponseDto[].class);
    }

    @Test
    public void T04_getAllCity_ShouldReturnIterable_CityResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllCity)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(CityResponseDto[].class);

    }

    @Test
    public void T05_findCreatedCity_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getCity())
                .when()
                .get(Path.server + Endpoint.findCityByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());


    }

    @Test
    public void T06_findCreatedCity_ShouldReturn_CityResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getCity())
                .when()
                .get(Path.server + Endpoint.findCityByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(CityResponseDto.class);
    }

    @Test
    public void T07_existCreatedCity_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getCity())
                .when()
                .get(Path.server + Endpoint.existCityByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T08_deleteCreatedCity_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getCity())
                .delete(Path.server + Endpoint.deleteCityByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T09_deleteCreatedCityAgain_ShouldReturnStatus_NOTFOUND(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getCity())
                .delete(Path.server + Endpoint.deleteCityByName)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .spec(TestSpecification.buildResponseSpec());

    }
}
