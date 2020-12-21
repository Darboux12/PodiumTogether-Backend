package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.NewsAddControllerRequest;
import com.podium.controller.dto.response.ContactControllerResponse;
import com.podium.controller.dto.response.NewsControllerResponse;
import com.podium.specification.TestSpecification;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class NewsValidator {

    private static NewsValidator instance;

    private NewsValidator() {}

    public static NewsValidator getInstance() {
        if(instance == null) {
            instance = new NewsValidator();
        }
        return instance;
    }

    public void add(NewsAddControllerRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)

                .multiPart(new MultiPartSpecBuilder(requestDto, ObjectMapperType.JACKSON_2)
                    .fileName("request.json")
                    .controlName("news")
                    .mimeType("application/vnd.custom+json").build())

                .multiPart(new MultiPartSpecBuilder("Test-Content-In-File".getBytes()).
                        fileName("image.jpg").
                        controlName("images").
                        mimeType("image/jpeg").
                        build())

                .when().post(PodiumPath.server + PodiumEndpoint.addNews)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<NewsControllerResponse> findAll(){

        NewsControllerResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllNews)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) NewsControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public NewsControllerResponse findByTitle(String title, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("title",title)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findNewsByTitle)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) NewsControllerResponse.class);

    }

    public List<ContactControllerResponse> findBySubject(String subject, HttpStatus status){

        ContactControllerResponse[] dtos =

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("subject",subject)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findAllContactBySubject)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public void deleteNewsById(int id, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when().delete(PodiumPath.server + PodiumEndpoint.deleteNewsById)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
