package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.RatingCategoryAddRequest;
import com.podium.controller.dto.response.RatingCategoryResponse;
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

    public void add(RatingCategoryAddRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post(PodiumPath.server + PodiumEndpoint.addRatingCategory)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<RatingCategoryResponse> findAll(HttpStatus status){

        RatingCategoryResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllRatingCategories)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) RatingCategoryResponse[].class);

        return Arrays.asList(dtos);

    }

    public RatingCategoryResponse findByCategory(String category, HttpStatus status){

        if(status == HttpStatus.OK)

            return  given()
                    .spec(TestSpecification.buildRequestSpec())
                    .pathParam("category",category)
                    .when()
                    .get(PodiumPath.server + PodiumEndpoint.findRatingCategory)
                    .then().assertThat()
                    .statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec())
                    .extract().as(RatingCategoryResponse.class);

        else given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("category",category)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findRatingCategory)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

        return null;

    }

    public boolean existCategory(String category, HttpStatus status){

        return

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("category",category)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existRatingCategory)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(boolean.class);


    }

    public void deleteCategory(String category, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("category",category)
                .delete(PodiumPath.server + PodiumEndpoint.deleteRatingCategory)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }
}
