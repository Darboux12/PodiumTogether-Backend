package com.podium.api;

import com.podium.helper.*;
import com.podium.model.dto.request.EventRequestDto;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventTest {

    private static EventRequestDto requestDto;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass() throws ParseException {
        TestLogger.setUp();
        requestDto = Constant.getValidEventRequestDto();
    }

    @Test
    public void T01_addEmptyTitle_ShouldReturnStatus_CONFLCT(){

        valueHolder = requestDto.getTitle();
        requestDto.setTitle("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T02_addToShortTitle_ShouldReturnStatus_CONFLCT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minEventTitleLength - 1);

        valueHolder = requestDto.getTitle();
        requestDto.setTitle(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T03_addToLongTitle_ShouldReturnStatus_CONFLCT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxEventTitleLength + 1);

        valueHolder = requestDto.getTitle();
        requestDto.setTitle(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T04_addDateFromInPast_ShouldReturnStatus_CONFLCT() throws ParseException {

        Date tmpDate = requestDto.getDateFrom();
        requestDto.setDateFrom(new SimpleDateFormat("dd/MM/yyyy")
                .parse("31/12/1700"));

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setDateFrom(tmpDate);

    }
    
    @Test
    public void T05_addDateToInPast_ShouldReturnStatus_CONFLCT() throws ParseException {

        Date tmpDate = requestDto.getDateTo();
        requestDto.setDateTo(new SimpleDateFormat("dd/MM/yyyy")
                .parse("31/12/1700"));

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setDateTo(tmpDate);

    }

    @Test
    public void T06_addEmptyCity_ShouldReturnStatus_CONFLCT(){

        valueHolder = requestDto.getCity();
        requestDto.setCity("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setCity(valueHolder);

    }
    
    @Test
    public void T07_addToShortCity_ShouldReturnStatus_CONFLCT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minCityLength - 1);

        valueHolder = requestDto.getCity();
        requestDto.setCity(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setCity(valueHolder);

    }

    @Test
    public void T08_addToLongCity_ShouldReturnStatus_CONFLCT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);

        valueHolder = requestDto.getCity();
        requestDto.setCity(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setCity(valueHolder);

    }

    @Test
    public void T09_addEmptyStreet_ShouldReturnStatus_CONFLCT(){

        valueHolder = requestDto.getStreet();
        requestDto.setStreet("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setStreet(valueHolder);

    }

    @Test
    public void T10_addToShortStreet_ShouldReturnStatus_CONFLCT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minStreetLength - 1);

        valueHolder = requestDto.getStreet();
        requestDto.setStreet(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setStreet(valueHolder);

    }

    @Test
    public void T11_addToLongStreet_ShouldReturnStatus_CONFLCT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxStreetLength + 1);

        valueHolder = requestDto.getStreet();
        requestDto.setStreet(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setStreet(valueHolder);

    }

    @Test
    public void T12_addEmptyNumber_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getNumber();
        requestDto.setNumber(0);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setNumber(valueHolderInt);

    }
    
    @Test
    public void T13_addToShortNumber_ShouldReturnStatus_CONFLCT(){
        
        int valueHolderInt;

        int toShort = PodiumLimits.minEventNumberLength - 1;

        valueHolderInt = requestDto.getNumber();
        requestDto.setNumber(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setNumber(valueHolderInt);

    }

    @Test
    public void T14_addToLongNumber_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventNumberLength + 1;

        valueHolderInt = requestDto.getNumber();
        requestDto.setNumber(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setNumber(valueHolderInt);

    }

    @Test
    public void T15_addEmptyPostal_ShouldReturnStatus_CONFLCT(){

        valueHolder = requestDto.getPostal();
        requestDto.setPostal("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPostal(valueHolder);

    }

    @Test
    public void T16_addToShortPostal_ShouldReturnStatus_CONFLCT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minPostalLength - 1);

        valueHolder = requestDto.getPostal();
        requestDto.setPostal(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPostal(valueHolder);

    }

    @Test
    public void T17_addToLongPostal_ShouldReturnStatus_CONFLCT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxPostalLength + 1);

        valueHolder = requestDto.getPostal();
        requestDto.setPostal(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPostal(valueHolder);

    }

    @Test
    public void T18_addEmptyDiscipline_ShouldReturnStatus_CONFLCT(){

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T19_addToShortDiscipline_ShouldReturnStatus_CONFLCT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T20_addToLongDiscipline_ShouldReturnStatus_CONFLCT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T21_addEmptyPeople_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getPeople();
        requestDto.setPeople(0);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPeople(valueHolderInt);

    }

    @Test
    public void T22_addToShortPeople_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toShort = PodiumLimits.minEventPeopleLength - 1;

        valueHolderInt = requestDto.getPeople();
        requestDto.setPeople(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPeople(valueHolderInt);

    }

    @Test
    public void T23_addToLongPeople_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventPeopleLength + 1;

        valueHolderInt = requestDto.getPeople();
        requestDto.setPeople(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setPeople(valueHolderInt);

    }

    @Test
    public void T24_addEmptyMinAge_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getMinAge();
        requestDto.setMinAge(0);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setMinAge(valueHolderInt);

    }

    @Test
    public void T25_addToShortMinAge_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toShort = PodiumLimits.minEventMinAge - 1;

        valueHolderInt = requestDto.getMinAge();
        requestDto.setMinAge(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setMinAge(valueHolderInt);

    }

    @Test
    public void T26_addToLongMinAge_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventMinAge + 1;

        valueHolderInt = requestDto.getMaxAge();
        requestDto.setMaxAge(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T27_addEmptyMaxAge_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getMaxAge();
        requestDto.setMaxAge(0);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T28_addToShortMaxAge_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toShort = PodiumLimits.maxEventMaxAge - 1;

        valueHolderInt = requestDto.getMaxAge();
        requestDto.setMaxAge(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T29_addToLongMaxAge_ShouldReturnStatus_CONFLCT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventMaxAge + 1;

        valueHolderInt = requestDto.getMaxAge();
        requestDto.setMaxAge(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        requestDto.setMaxAge(valueHolderInt);

    }
    
    
    
    













    /*

    @Test
    public void T01_addValidEvent_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_addSameEventAgain_ShouldReturnStatus_CONFLICT(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_getAllEvent_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + Endpoint.findAllEvent)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T04_getAllEvent_ShouldReturnIterable_EventResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllEvent)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(EventResponseDto[].class);

    }

    @Test
    public void T05_findCreatedEvent_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getEvent())
                .when()
                .get(Path.server + Endpoint.findEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());


    }

    @Test
    public void T06_findCreatedEvent_ShouldReturn_EventResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getEvent())
                .when()
                .get(Path.server + Endpoint.findEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(EventResponseDto.class);
    }

    @Test
    public void T07_existCreatedEvent_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getEvent())
                .when()
                .get(Path.server + Endpoint.existEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T08_deleteCreatedEvent_ShouldReturnStatus_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getEvent())
                .delete(Path.server + Endpoint.deleteEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T09_deleteCreatedEventAgain_ShouldReturnStatus_NOTFOUND(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getEvent())
                .delete(Path.server + Endpoint.deleteEventByName)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .spec(TestSpecification.buildResponseSpec());

    }


     */
}
