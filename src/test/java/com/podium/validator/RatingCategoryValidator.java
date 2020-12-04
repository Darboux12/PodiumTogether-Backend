package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RatingCategoryValidator {

    private static RatingCategoryValidator instance;

    private RatingCategoryValidator() {}

    public static RatingCategoryValidator getInstance() {
        if(instance == null) {
            instance = new RatingCategoryValidator();
        }
        return instance;
    }

    public void add(RatingCategoryRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(Path.server + PodiumEndpoint.addRatingCategory)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<RatingCategoryResponseDto> findAll(HttpStatus status){

        RatingCategoryResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + PodiumEndpoint.findAllRatingCategories)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) RatingCategoryResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public RatingCategoryResponseDto findByCategory(String category, HttpStatus status){

        if(status == HttpStatus.OK)

            return  given()
                    .spec(TestSpecification.buildRequestSpec())
                    .pathParam("category",category)
                    .when()
                    .get(Path.server + PodiumEndpoint.findRatingCategory)
                    .then().assertThat()
                    .statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec())
                    .extract().as(RatingCategoryResponseDto.class);

        else given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("category",category)
                .when()
                .get(Path.server + PodiumEndpoint.findRatingCategory)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

        return null;

    }

    public void existCategory(String category, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("category",category)
                .when()
                .get(Path.server + PodiumEndpoint.existRatingCategory)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteCategory(String category, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("category",category)
                .delete(Path.server + PodiumEndpoint.deleteRatingCategory)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }
}
