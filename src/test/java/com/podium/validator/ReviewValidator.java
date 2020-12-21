package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.response.ReviewControllerResponse;
import com.podium.controller.dto.request.ReviewAddControllerRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.mapper.ObjectMapperType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import com.podium.specification.TestSpecification;
import org.springframework.http.MediaType;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ReviewValidator {

    private static ReviewValidator instance;

    private ReviewValidator() {}

    public static ReviewValidator getInstance() {
        if(instance == null) {
            instance = new ReviewValidator();
        }
        return instance;
    }

    public void add(ReviewAddControllerRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)

                .multiPart(new MultiPartSpecBuilder(requestDto, ObjectMapperType.JACKSON_2)
                        .fileName("request.json")
                        .controlName("review")
                        .mimeType("application/vnd.custom+json").build())

                .multiPart(new MultiPartSpecBuilder("Test-Content-In-File".getBytes()).
                        fileName("image.jpg").
                        controlName("images").
                        mimeType("image/jpeg").
                        build())

                .when().post(PodiumPath.server + PodiumEndpoint.addReview)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<ReviewControllerResponse> findAllReviewsByAuthor(String userName, HttpStatus status){

        ReviewControllerResponse[] responses =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .pathParam("username",userName)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findReviewsByAuthor)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ReviewControllerResponse[].class);

        return Arrays.asList(responses);

    }

    public void deleteReviewById(int id, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("id",id)
                .delete(PodiumPath.server + PodiumEndpoint.deleteReviewById)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void incrementReviewLikesById(int id, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("id",id)
                .patch(PodiumPath.server + PodiumEndpoint.incrementReviewLikes)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void incrementReviewDislikesById(int id, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("id",id)
                .patch(PodiumPath.server + PodiumEndpoint.incrementReviewDislikes)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
