package com.podium.TestValidator;

import com.podium.helper.Endpoint;
import com.podium.helper.Path;
import com.podium.helper.TestSpecification;
import com.podium.model.dto.RequestDto;
import com.podium.model.dto.ResponseDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.CountryResponseDto;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;

import static io.restassured.RestAssured.given;

public class TestValidator {

    private static TestValidator instance;

    private TestValidator() {}

    public static TestValidator getInstance() {
        if(instance == null) {
            instance = new TestValidator();
        }
        return instance;
    }

    public void performAddingValidDtoTest(
            RequestDto requestDto,
            String endpoint,
            HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + endpoint)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void performAddingDtoRmptyTextFieldTest(
            RequestDto requestDto,
            String emptyField,
            String endpoint,
            HttpStatus status) throws NoSuchFieldException, IllegalAccessException {

        Field field = requestDto.getClass().getDeclaredField(emptyField);
        field.setAccessible(true);

        if(field.getType().equals(String.class)){

            String valueHolder = (String) field.get(requestDto);

            field.set(requestDto,"");

            given()
                    .spec(TestSpecification.buildRequestSpec())
                    .contentType(ContentType.JSON)
                    .when().get(Path.server + endpoint)
                    .then().assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .spec(TestSpecification.buildResponseSpec());

            field.set(requestDto,valueHolder);

        }

        Assert.fail();

    }






}
