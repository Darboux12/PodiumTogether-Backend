package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.dto.response.NewsResponseDto;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

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

    public void add(NewsRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addNews)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<NewsResponseDto> findAll(){

        NewsResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllNews)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type)NewsResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public NewsResponseDto findByTitle(String title, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("title",title)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findNewsByTitle)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) NewsResponseDto.class);

    }

    public List<ContactResponseDto> findBySubject(String subject, HttpStatus status){

        ContactResponseDto[] dtos =

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("subject",subject)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findAllContactBySubject)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactResponseDto[].class);

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
