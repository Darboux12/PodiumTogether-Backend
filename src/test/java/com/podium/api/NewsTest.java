package com.podium.api;

import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.NewsResponseDto;
import com.podium.specification.TestSpecification;
import com.podium.validation.validators.PodiumLimits;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
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
public class NewsTest {

    private static NewsRequestDto newsRequest;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){

            TestLogger.setUp();

            newsRequest = Constant.getValidNewsRequest();
    }

    @Test
    public void T01_getAllNews_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + Endpoint.findAllNews)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_getAllNews_ShouldReturnIterable_NewsResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllNews)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(NewsResponseDto[].class);

    }

    @Test
    public void T03_addNewsWithOutTitle_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setTitle(valueHolder);

    }

    @Test
    public void T04_addNewsWithOutFullText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getText();
        newsRequest.setText("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setText(valueHolder);

    }

    @Test
    public void T05_addNewsWithOutShortText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setShortText(valueHolder);

    }

    @Test
    public void T06_addNewsWithOutLinkText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    public void T07_addValidNews_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());



    }

    @Test
    public void T08_addSameNewsAgain_ShouldReturnStatus_CONFLICT(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());



    }

    @Test
    public void T09_findAndDeleteCreatedValidNews_ShouldReturnStatus_OK(){

        NewsResponseDto newsResponseDto =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("title",newsRequest.getTitle())
                        .when().get(Path.server + Endpoint.findNewsByTitle)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(NewsResponseDto.class);


        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("id", newsResponseDto.getId())
                .when().delete(Path.server + Endpoint.deleteNewsById)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T10_addNewsToShortTitle_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsTitleLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setTitle(valueHolder);

    }

    @Test
    public void T11_addNewsToShortShortText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsShortTextLength - 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setShortText(valueHolder);

    }

    @Test
    public void T12_addNewsToShortFullText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsFullTextLength - 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setText(valueHolder);

    }

    @Test
    public void T13_addNewsToShortLinkText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsLinkTextLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setLinkText(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    public void T14_addNewsToLongTitle_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsTitleLength + 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setTitle(valueHolder);

    }

    @Test
    public void T15_addNewsToLongShortText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsShortTextLength + 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setShortText(valueHolder);

    }

    @Test
    public void T16_addNewsToLongFullText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsFullTextLength + 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setText(valueHolder);

    }

    @Test
    public void T17_addNewsToLongLinkText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsLinkTextLength+ 1);

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(newsRequest)
                .when().post(Path.server + Endpoint.addNews)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        newsRequest.setLinkText(valueHolder);

    }

}
