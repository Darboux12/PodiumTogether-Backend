package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.other.*;
import com.podium.controller.dto.request.NewsAddRequest;
import com.podium.controller.dto.request.PlaceAddRequest;
import com.podium.controller.dto.request.ReviewAddRequest;
import com.podium.controller.dto.request.SignUpRequest;
import com.podium.logger.TestLogger;
import com.podium.validator.PlaceValidator;
import com.podium.validator.ReviewValidator;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ReviewTest {

    private static String usernameOne = "TEST USERNAME_ONE";
    private static String usernameTwo = "TEST USERNAME_TWO";

    private static String placeName = "TEST PLACE NAME";

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    private static Stream<SignUpRequest> provideTwoUsersSignUpRequestsForTests() throws ParseException {

        return Stream.of(

                 new SignUpRequest(
                         usernameOne ,
                        "TEST_MAIL_ONE@gmail.com",
                        "TEST PASSWORD ONE",
                        "POLAND",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
                ),

                 new SignUpRequest(
                         usernameTwo,
                        "TEST_MAIL_TWO@gmail.com",
                        "TEST PASSWORD TWO",
                        "POLAND",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
                )
        );

    }

    private static Stream<PlaceAddRequest> providePlaceForTests(){

        LocalizationDto localizationDto = new LocalizationDto(
                placeName,
                "PlaceTestStreetName",
                123,
                "24-060",
                "Place test localization remarks"
        );

        List<BusinessDayDto> businessDayDtos = new LinkedList<>();

        LocalTime timeFrom = LocalTime.parse("10:33:22");
        LocalTime timeTo = LocalTime.parse("17:00");

        businessDayDtos.add(new BusinessDayDto("Monday",true,
                timeFrom,timeTo));
        businessDayDtos.add(new BusinessDayDto("Tuesday",true,
                timeFrom,timeTo));
        businessDayDtos.add(new BusinessDayDto("Wednesday",true,
                timeFrom,timeTo));
        businessDayDtos.add(new BusinessDayDto("Thursday",true,
                timeFrom,timeTo));
        businessDayDtos.add(new BusinessDayDto("Friday",true,
                timeFrom,timeTo));
        businessDayDtos.add(new BusinessDayDto("Saturday",true,
                timeFrom,timeTo));
        businessDayDtos.add(new BusinessDayDto("Sunday",true,
                timeFrom,timeTo));


        return Stream.of(
                   new PlaceAddRequest(
                            placeName,
                            "Football",
                            localizationDto,
                            businessDayDtos,
                            50,
                            PodiumLimits.minUsageTimeHours + 1,
                            10,
                            20
                    )
        );

    }

    private static Stream<ReviewAddRequest> provideEmptyReviewsForTests() {

        var ratings = new HashSet<RatingDto>();

        ratings.add(new RatingDto("Service",2));
        ratings.add(new RatingDto("Equipment",4));
        ratings.add(new RatingDto("Price",5));

        var fileList = new ArrayList<PodiumFileDto>();

        var emptySet = new HashSet<RatingDto>();


        return Stream.of(

                new ReviewAddRequest("",placeName,ratings,"Opinion"),
                new ReviewAddRequest(usernameOne,"",ratings,"Opinion"),
                new ReviewAddRequest(usernameTwo,placeName,emptySet,"Opinion"),
                new ReviewAddRequest(usernameOne,placeName,ratings,"")

        );

    }

    private static Stream<ReviewAddRequest> provideTooLongAndTooShortReviewsForTests() {

        var ratings = new HashSet<RatingDto>();

        ratings.add(new RatingDto("Service",2));
        ratings.add(new RatingDto("Equipment",4));
        ratings.add(new RatingDto("Price",5));

        var emptySet = new HashSet<RatingDto>();

        return Stream.of(

                new ReviewAddRequest(StringUtils.repeat("*", PodiumLimits.maxUsernameLength + 1),placeName,ratings,"Opinion"),
                new ReviewAddRequest(StringUtils.repeat("*", PodiumLimits.minUsernameLength - 1),placeName,ratings,"Opinion"),
                new ReviewAddRequest(usernameOne,StringUtils.repeat("*", PodiumLimits.maxPlaceNameLength + 1),ratings,"Opinion"),
                new ReviewAddRequest(usernameOne,StringUtils.repeat("*", PodiumLimits.minPlaceNameLength - 1),ratings,"Opinion"),
                new ReviewAddRequest(usernameTwo,placeName,emptySet,"Opinion"),
                new ReviewAddRequest(usernameOne,placeName,ratings,StringUtils.repeat("*", PodiumLimits.maxPlaceReviewOpinion + 1)),
                new ReviewAddRequest(usernameOne,placeName,ratings,StringUtils.repeat("*", PodiumLimits.minPlaceReviewOpinion - 1))

        );

    }

    private static Stream<ReviewAddRequest> provideRequestNotExistingPlaceAndUser() {

        var ratings = new HashSet<RatingDto>();

        ratings.add(new RatingDto("Service",2));
        ratings.add(new RatingDto("Equipment",4));
        ratings.add(new RatingDto("Price",5));

        return Stream.of(

                new ReviewAddRequest(
                        "NOT EXISTING USERNAME",
                        placeName,
                        ratings,
                        "This is test opinion about place"),

                new ReviewAddRequest(
                        usernameOne,
                        "NOT EXISTING PLACE NAME",
                        ratings,
                        "This is test opinion about place")
        );

    }

    private static Stream<ReviewAddRequest> provideValidRequest() {

        var ratings = new HashSet<RatingDto>();

        ratings.add(new RatingDto("Service",2));
        ratings.add(new RatingDto("Equipment",4));
        ratings.add(new RatingDto("Price",5));

        return Stream.of(

                new ReviewAddRequest(
                        usernameOne,
                        placeName,
                        ratings,
                        "This is test opinion about place")
        );

    }

    @ParameterizedTest
    @MethodSource("provideTwoUsersSignUpRequestsForTests")
    void T01_Sign_Up_Users_Who_Add_Review(SignUpRequest signUpRequest){

        UserValidator
                .getInstance()
                .signUp(signUpRequest, HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("providePlaceForTests")
    void T02_Add_Place_That_Is_Reviewed(PlaceAddRequest requestDto){

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyReviewsForTests")
    void T03_Add_Review_Empty_Values_Status_CONFLICT(ReviewAddRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortReviewsForTests")
    void T04_Add_Review_TooLong_TooShort_Values_Status_CONFLICT(ReviewAddRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideRequestNotExistingPlaceAndUser")
    void T05_Add_Review_Not_Existing_User_And_Place_Status_NOT_FOUND(ReviewAddRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T06_Add_Valid_Review_Status_OK_Add_Entity(ReviewAddRequest requestDto){

       ReviewValidator.getInstance().add(requestDto,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T06_Find_All_Reviews_By_Author_Status_OK_Correct_PlaceName(ReviewAddRequest requestDto){

        List<String> actualPlaceNames = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),HttpStatus.OK)
                .stream().map(ReviewResponse::getPlace)
                .forEach(actualPlaceNames::add);

        Assertions.assertTrue(actualPlaceNames.contains(requestDto.getPlace()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T07_Increment_Review_Likes_Status_OK(ReviewAddRequest requestDto){

        List<ReviewResponse> responses = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responses.forEach(x -> ReviewValidator
                .getInstance()
                .incrementReviewLikesById(x.getId(),HttpStatus.OK));

        List<ReviewResponse> responsesAgain = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responsesAgain.forEach(x -> Assertions.assertEquals(1,x.getLikes()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T08_Increment_Review_Dislikes_Status_OK(ReviewAddRequest requestDto){

        List<ReviewResponse> responses = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responses.forEach(x -> ReviewValidator
                .getInstance()
                .incrementReviewDislikesById(x.getId(),HttpStatus.OK));

        List<ReviewResponse> responsesAgain = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responsesAgain.forEach(x -> Assertions.assertEquals(1,x.getDislikes()));

    }



    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T09_Delete_All_Reviews_By_Id_Status_OK_Delete_Reviews(ReviewAddRequest requestDto){

        List<Integer> ids = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),HttpStatus.OK)
                .stream().map(ReviewResponse::getId)
                .forEach(ids::add);

        ids.forEach(x -> ReviewValidator.getInstance().deleteReviewById(x,HttpStatus.OK));

    }







    @ParameterizedTest
    @MethodSource("providePlaceForTests")
    void T41_Delete_Created_Place(PlaceAddRequest requestDto){

        int id = PlaceValidator

                .getInstance()
                .findByName(requestDto.getName(),HttpStatus.OK)
                .getId();

        PlaceValidator.getInstance().deletePlaceById(id,HttpStatus.OK);

    }





    @ParameterizedTest
    @MethodSource("provideTwoUsersSignUpRequestsForTests")
    void T45_Delete_Created_Users(SignUpRequest signUpRequest){

        UserValidator
                .getInstance()
                .deleteUserByUsername(signUpRequest.getUsername(),HttpStatus.OK);

    }





}
