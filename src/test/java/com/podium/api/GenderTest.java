package com.podium.api;



import com.podium.constant.PodiumEndpoint;
import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.specification.TestSpecification;
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
public class GenderTest {

    private static GenderRequestDto requestDto;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = Constant.getValidGenderRequestDto();
    }

    @Test
    public void T01_addValidGender_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + PodiumEndpoint.addGender)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_addSameGenderAgain_ShouldReturnStatus_CONFLICT(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + PodiumEndpoint.addGender)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_getAllGender_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + PodiumEndpoint.findAllGender)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T04_getAllGender_ShouldReturnIterable_GenderResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + PodiumEndpoint.findAllGender)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(GenderResponseDto[].class);

    }

    @Test
    public void T05_findCreatedGender_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getGender())
                .when()
                .get(Path.server + PodiumEndpoint.findGenderByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());


    }

    @Test
    public void T06_findCreatedGender_ShouldReturn_GenderResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getGender())
                .when()
                .get(Path.server + PodiumEndpoint.findGenderByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(GenderResponseDto.class);
    }

    @Test
    public void T07_existCreatedGender_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getGender())
                .when()
                .get(Path.server + PodiumEndpoint.existGenderByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T08_deleteCreatedGender_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getGender())
                .delete(Path.server + PodiumEndpoint.deleteGenderByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T09_deleteCreatedGenderAgain_ShouldReturnStatus_NOTFOUND(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getGender())
                .delete(Path.server + PodiumEndpoint.deleteGenderByName)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
