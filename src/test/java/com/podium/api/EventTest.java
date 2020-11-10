package com.podium.api;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.EventRequestDto;
import com.podium.specification.TestSpecification;
import com.podium.constant.PodiumLimits;
import com.podium.validator.EventValidator;
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
    public void T01_Add_Event_With_Empty__Title_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getTitle();
        requestDto.setTitle("");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T02_Add_Event_To_Short_Title_Should_Return_Status_CONFLICT(){

        String toShort = StringUtils
                .repeat("*", PodiumLimits.minEventTitleLength - 1);

        valueHolder = requestDto.getTitle();
        requestDto.setTitle(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T03_Add_To_Long_Title_Should_Return_Status_CONFLICT(){

        String To_Long_ = StringUtils.repeat("*", PodiumLimits.maxEventTitleLength + 1);

        valueHolder = requestDto.getTitle();
        requestDto.setTitle(To_Long_);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T04_Add_Date_From_In_Past_Should_Return_Status_CONFLICT() throws ParseException {

        Date tmpDate = requestDto.getDateFrom();
        requestDto.setDateFrom(new SimpleDateFormat("dd/MM/yyyy")
                .parse("31/12/1700"));

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDateFrom(tmpDate);

    }
    
    @Test
    public void T05_Add_DateToInPast_Should_Return_Status_CONFLICT() throws ParseException {

        Date tmpDate = requestDto.getDateTo();
        requestDto.setDateTo(new SimpleDateFormat("dd/MM/yyyy")
                .parse("31/12/1700"));

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDateTo(tmpDate);

    }

    @Test
    public void T06_Add_Empty_City_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getCity();
        requestDto.setCity("");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setCity(valueHolder);

    }
    
    @Test
    public void T07_Add_ToShortCity_Should_Return_Status_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minCityLength - 1);

        valueHolder = requestDto.getCity();
        requestDto.setCity(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setCity(valueHolder);

    }

    @Test
    public void T08_Add_To_Long_City_Should_Return_Status_CONFLICT(){

        String To_Long_ = StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);

        valueHolder = requestDto.getCity();
        requestDto.setCity(To_Long_);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setCity(valueHolder);

    }

    @Test
    public void T09_Add_Empty_Street_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getStreet();
        requestDto.setStreet("");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setStreet(valueHolder);

    }

    @Test
    public void T10_Add_To_Short_Street_Should_Return_Status_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minStreetLength - 1);

        valueHolder = requestDto.getStreet();
        requestDto.setStreet(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setStreet(valueHolder);

    }

    @Test
    public void T11_Add_To_Long_Street_Should_Return_Status_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxStreetLength + 1);

        valueHolder = requestDto.getStreet();
        requestDto.setStreet(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setStreet(valueHolder);

    }

    @Test
    public void T12_Add_To_ShortNumber_Should_Return_Status_CONFLICT(){
        
        int valueHolderInt;

        int toShort = PodiumLimits.minBuildingNumberLength - 1;

        valueHolderInt = requestDto.getNumber();
        requestDto.setNumber(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setNumber(valueHolderInt);

    }

    @Test
    public void T13_Add_To_Long_Number_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxBuildingNumberLength + 1;

        valueHolderInt = requestDto.getNumber();
        requestDto.setNumber(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);;

        requestDto.setNumber(valueHolderInt);

    }

    @Test
    public void T14_Add_Empty_Postal_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getPostal();
        requestDto.setPostal("");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPostal(valueHolder);

    }

    @Test
    public void T15_Add_To_ShortPostal_Should_Return_Status_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minPostalLength - 1);

        valueHolder = requestDto.getPostal();
        requestDto.setPostal(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPostal(valueHolder);

    }

    @Test
    public void T16_Add_To_Long_Postal_Should_Return_Status_CONFLICT(){

        String toLong= StringUtils.repeat("*", PodiumLimits.maxPostalLength + 1);

        valueHolder = requestDto.getPostal();
        requestDto.setPostal(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPostal(valueHolder);

    }

    @Test
    public void T17_Add_Empty_Discipline_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline("");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T18_Add_To_Short_Discipline_Should_Return_Status_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T19_Add_To_Long_Discipline_Should_Return_Status_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T20_Add_Empty_People_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getPeople();
        requestDto.setPeople(0);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPeople(valueHolderInt);

    }

    @Test
    public void T21_Add_To_Short_People_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toShort = PodiumLimits.minEventPeopleLength - 1;

        valueHolderInt = requestDto.getPeople();
        requestDto.setPeople(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPeople(valueHolderInt);

    }

    @Test
    public void T22_Add_To_Long_People_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventPeopleLength + 1;

        valueHolderInt = requestDto.getPeople();
        requestDto.setPeople(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPeople(valueHolderInt);

    }

    @Test
    public void T23_Add_Empty_Min_Age_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getMinAge();
        requestDto.setMinAge(0);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMinAge(valueHolderInt);

    }

    @Test
    public void T24_Add_To_ShortMin_Age_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toShort = PodiumLimits.minEventMinAge - 1;

        valueHolderInt = requestDto.getMinAge();
        requestDto.setMinAge(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMinAge(valueHolderInt);

    }

    @Test
    public void T25_Add_To_Long_Min_Age_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventMinAge + 1;

        valueHolderInt = requestDto.getMaxAge();
        requestDto.setMaxAge(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T26_Add_Empty_Max_Age_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        valueHolderInt= requestDto.getMaxAge();
        requestDto.setMaxAge(0);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T27_Add_To_Short_MaxAge_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toShort = PodiumLimits.minEventMaxAge - 1;

        valueHolderInt = requestDto.getMaxAge();
        requestDto.setMaxAge(toShort);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T28_Add_To_Long_MaxAge_Should_Return_Status_CONFLICT(){

        int valueHolderInt;

        int toLong =  PodiumLimits.maxEventMaxAge + 1;

        valueHolderInt = requestDto.getMaxAge();
        requestDto.setMaxAge(toLong);

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(valueHolderInt);

    }

    @Test
    public void T29_Add_Valid_Event_Should_Return_Status_OK(){

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

    }

    @Test
    public void T30_Add_Same_Event_Should_Return_Status_CONFLICT(){

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

    }

    @Test
    public void T31_Add_Event_Nonexistent_Discipline_Should_Return_Status_CONFLICT(){

        String valueHolder = requestDto.getTitle();

        requestDto.setTitle("AnotherEventTitle");

        requestDto.setDiscipline("NonExistentDiscipline");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setTitle(valueHolder);

    }

    @Test
    public void T32_Add_Event_Nonexistent_User_Should_Return_Status_CONFLICT(){

        String author = requestDto.getAuthor();
        String title = requestDto.getTitle();

        requestDto.setTitle("AnotherEventTitle");
        requestDto.setAuthor("NonExistentUsername");

        EventValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setTitle(title);
        requestDto.setAuthor(author);

    }

    @Test
    public void T33_Delete_Created_Event_Should_Return_Status_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("title",requestDto.getTitle())
                .when().delete(Path.server + PodiumEndpoint.deleteEvent)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    /*

    @Test
    public void T01_Add_ValidEvent_Should_Return_Status_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.AddEvent)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_Add_SameEventAgain_Should_Return_Status_CONFLICT(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.AddEvent)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T03_getAllEvent_Should_Return_Status_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(PodiumPath.server + PodiumEndpoint.findAllEvent)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T04_getAllEvent_ShouldReturnIterable_EventResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findAllEvent)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(EventResponseDto[].class);

    }

    @Test
    public void T05_findCreatedEvent_Should_Return_Status_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getEvent())
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());


    }

    @Test
    public void T06_findCreatedEvent_ShouldReturn_EventResponse(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getEvent())
                .when()
                .get(PodiumPath.server + PodiumEndpoint.findEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(EventResponseDto.class);
    }

    @Test
    public void T07_existCreatedEvent_Should_Return_Status_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",requestDto.getEvent())
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T08_deleteCreatedEvent_Should_Return_Status_OK(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getEvent())
                .delete(PodiumPath.server + PodiumEndpoint.deleteEventByName)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T09_deleteCreatedEventAgain_Should_Return_Status_NOTFOUND(){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",requestDto.getEvent())
                .delete(PodiumPath.server + PodiumEndpoint.deleteEventByName)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .spec(TestSpecification.buildResponseSpec());

    }


     */
}
