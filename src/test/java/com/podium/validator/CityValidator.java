package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.specification.TestSpecification;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class CityValidator {

    private static CityValidator instance;

    private CityValidator() {}

    public static CityValidator getInstance() {
        if(instance == null) {
            instance = new CityValidator();
        }
        return instance;
    }

    public void add(CityRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addCity)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public Stream<Arguments> provideArgumentsForAddTest(){

        return Stream.of(
                Arguments.of("",HttpStatus.OK)

        );

    }

    public List<CityResponseDto> findAll(){

        CityResponseDto[] dtos =

        given()
          .spec(TestSpecification.buildRequestSpec())
          .contentType(ContentType.JSON)
          .when().get(PodiumPath.server + PodiumEndpoint.findAllCity)
          .then().assertThat()
          .statusCode(HttpStatus.OK.value())
          .spec(TestSpecification.buildResponseSpec())
          .extract().as((Type)CityResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public CityResponseDto findByName(String cityName, HttpStatus status){

        if(status == HttpStatus.OK)

        return given().spec(TestSpecification.buildRequestSpec())
                  .contentType(ContentType.JSON)
                  .pathParam("name",cityName)
                  .when()
                  .get(PodiumPath.server + PodiumEndpoint.findCityByName)
                  .then().assertThat().statusCode(status.value())
                  .spec(TestSpecification.buildResponseSpec())
                  .extract().as(CityResponseDto.class);

        else given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",cityName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

        return null;

    }

    public boolean existCityByName(String cityName, HttpStatus status){

        return

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",cityName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(boolean.class);

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
