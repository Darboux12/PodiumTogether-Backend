package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.other.BusinessDayControllerDto;
import com.podium.controller.dto.other.LocalizationControllerDto;
import com.podium.controller.dto.request.EventAddControllerRequest;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.controller.dto.request.PlaceAddControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.validator.EventValidator;
import com.podium.validator.PlaceValidator;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class EventTest {

 private static EventAddControllerRequest requestDto;
 private static String textValueHolder;
 private static int intValueHolder;
 private static double doubleValueHolder;

 private static String adminToken = "";
 private static String subscriberToken = "";

 @BeforeAll
 static void beforeClass() throws ParseException {

  TestLogger.setUp();

  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  LocalDateTime timeFrom = LocalDateTime.parse("2050-02-01 12:00:00");
  LocalDateTime timeTo = LocalDateTime.parse("2050-02-04 13:00:00");

  requestDto = new EventAddControllerRequest(
          "TEST_EVENT_NAME",
          timeFrom,
          timeTo,
          10,
          Set.of("Male"),
          10,
          30,
          "THIS IS EVENT TEST DESCRIPTION",
          "Test Place Name"
  );

 }

   private static Stream<String> provideEmptyValuesForTests(){
  return Stream.of("", " ", "  ","                    ");
 }

   @Test
   void T01_Sign_In_As_Admin_User_Get_Token(){

    adminToken =

            UserValidator
                    .getInstance()
                    .signIn(new JwtControllerRequest("admin","adminadmin"), HttpStatus.OK)
                    .getToken();

   }

   @Test
   void T02_Sign_In_As_Subscriber_User_Get_Token(){

    subscriberToken =

            UserValidator
                    .getInstance()
                    .signIn(new JwtControllerRequest("TEST USERNAME_ONE","TEST PASSWORD ONE"),HttpStatus.OK)
                    .getToken();

   }

   @ParameterizedTest
   @MethodSource("provideEmptyValuesForTests")
   void T03_Add_Event_Empty_Title_Return_Status_CONFLICT(String emptyName){

    textValueHolder = requestDto.getTitle();
    requestDto.setTitle(emptyName);

    EventValidator
            .getInstance()
            .addEvent(requestDto,subscriberToken, HttpStatus.CONFLICT);

    requestDto.setTitle(textValueHolder);
   }

   @Test
   void T04_Add_Event_Too_Title_Return_Status_CONFLICT(){

    String toShort =
            StringUtils.repeat("*", PodiumLimits.minEventTitleLength - 1);
    textValueHolder = requestDto.getTitle();
    requestDto.setTitle(toShort);

    EventValidator
            .getInstance()
            .addEvent(requestDto, adminToken,HttpStatus.CONFLICT);

    requestDto.setTitle(textValueHolder);

   }

   @Test
   void T05_Add_Place_Too_Long_Name_Return_Status_CONFLICT(){

    String toLong =
            StringUtils.repeat("*", PodiumLimits.maxEventTitleLength + 1);
    textValueHolder = requestDto.getTitle();
    requestDto.setTitle(toLong);

    EventValidator
            .getInstance()
            .addEvent(requestDto, adminToken,HttpStatus.CONFLICT);

    requestDto.setTitle(textValueHolder);

   }

   @Test
   void T36_Add_Valid_Event_Should_Return_Status_OK(){

    EventValidator
            .getInstance()
            .addEvent(requestDto, adminToken,HttpStatus.OK);

   }

   @Test
   void T43_Delete_Created_Event_Should_Return_Status_OK_Delete_Entity(){

    int id = EventValidator

            .getInstance()
            .findEventByTitle(requestDto.getTitle(),subscriberToken,HttpStatus.OK)
            .getId();

    EventValidator.getInstance().deleteEventById(id,adminToken,HttpStatus.OK);

   }

}
