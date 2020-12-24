package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.other.*;
import com.podium.controller.dto.request.PlaceAddControllerRequest;
import com.podium.controller.dto.request.ReviewAddControllerRequest;
import com.podium.controller.dto.request.SignUpControllerRequest;
import com.podium.controller.dto.response.ReviewControllerResponse;
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

    private static Stream<SignUpControllerRequest> provideTwoUsersSignUpRequestsForTests() throws ParseException {

        return Stream.of(

                 new SignUpControllerRequest(
                         usernameOne ,
                        "TEST_MAIL_ONE@gmail.com",
                        "TEST PASSWORD ONE",
                        "POLAND",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
                ),

                 new SignUpControllerRequest(
                         usernameTwo,
                        "TEST_MAIL_TWO@gmail.com",
                        "TEST PASSWORD TWO",
                        "POLAND",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
                )
        );

    }

    private static Stream<PlaceAddControllerRequest> providePlaceForTests(){

        LocalizationControllerDto localizationControllerDto = new LocalizationControllerDto(
                placeName,
                "PlaceTestStreetName",
                123,
                "24-060",
                "Place test localization remarks"
        );

        List<BusinessDayControllerDto> businessDayControllerDtos = new LinkedList<>();

        LocalTime timeFrom = LocalTime.parse("10:33:22");
        LocalTime timeTo = LocalTime.parse("17:00");

        businessDayControllerDtos.add(new BusinessDayControllerDto("Monday",true,
                timeFrom,timeTo));
        businessDayControllerDtos.add(new BusinessDayControllerDto("Tuesday",true,
                timeFrom,timeTo));
        businessDayControllerDtos.add(new BusinessDayControllerDto("Wednesday",true,
                timeFrom,timeTo));
        businessDayControllerDtos.add(new BusinessDayControllerDto("Thursday",true,
                timeFrom,timeTo));
        businessDayControllerDtos.add(new BusinessDayControllerDto("Friday",true,
                timeFrom,timeTo));
        businessDayControllerDtos.add(new BusinessDayControllerDto("Saturday",true,
                timeFrom,timeTo));
        businessDayControllerDtos.add(new BusinessDayControllerDto("Sunday",true,
                timeFrom,timeTo));


        return Stream.of(
                   new PlaceAddControllerRequest(
                            placeName,
                            "Football",
                           localizationControllerDto,
                           businessDayControllerDtos,
                            50,
                            PodiumLimits.minUsageTimeHours + 1,
                            10,
                            20
                    )
        );

    }

    private static Stream<ReviewAddControllerRequest> provideEmptyReviewsForTests() {

        var ratings = new HashSet<StarRatingControllerDto>();

        ratings.add(new StarRatingControllerDto("Service",2));
        ratings.add(new StarRatingControllerDto("Equipment",4));
        ratings.add(new StarRatingControllerDto("Price",5));

        var fileList = new ArrayList<FileControllerDto>();

        var emptySet = new HashSet<StarRatingControllerDto>();


        return Stream.of(

                new ReviewAddControllerRequest("",placeName,ratings,"Opinion"),
                new ReviewAddControllerRequest(usernameOne,"",ratings,"Opinion"),
                new ReviewAddControllerRequest(usernameTwo,placeName,emptySet,"Opinion"),
                new ReviewAddControllerRequest(usernameOne,placeName,ratings,"")

        );

    }

    private static Stream<ReviewAddControllerRequest> provideTooLongAndTooShortReviewsForTests() {

        var ratings = new HashSet<StarRatingControllerDto>();

        ratings.add(new StarRatingControllerDto("Service",2));
        ratings.add(new StarRatingControllerDto("Equipment",4));
        ratings.add(new StarRatingControllerDto("Price",5));

        var emptySet = new HashSet<StarRatingControllerDto>();

        return Stream.of(

                new ReviewAddControllerRequest(StringUtils.repeat("*", PodiumLimits.maxUsernameLength + 1),placeName,ratings,"Opinion"),
                new ReviewAddControllerRequest(StringUtils.repeat("*", PodiumLimits.minUsernameLength - 1),placeName,ratings,"Opinion"),
                new ReviewAddControllerRequest(usernameOne,StringUtils.repeat("*", PodiumLimits.maxPlaceNameLength + 1),ratings,"Opinion"),
                new ReviewAddControllerRequest(usernameOne,StringUtils.repeat("*", PodiumLimits.minPlaceNameLength - 1),ratings,"Opinion"),
                new ReviewAddControllerRequest(usernameTwo,placeName,emptySet,"Opinion"),
                new ReviewAddControllerRequest(usernameOne,placeName,ratings,StringUtils.repeat("*", PodiumLimits.maxPlaceReviewOpinion + 1)),
                new ReviewAddControllerRequest(usernameOne,placeName,ratings,StringUtils.repeat("*", PodiumLimits.minPlaceReviewOpinion - 1))

        );

    }

    private static Stream<ReviewAddControllerRequest> provideRequestNotExistingPlaceAndUser() {

        var ratings = new HashSet<StarRatingControllerDto>();

        ratings.add(new StarRatingControllerDto("Service",2));
        ratings.add(new StarRatingControllerDto("Equipment",4));
        ratings.add(new StarRatingControllerDto("Price",5));

        return Stream.of(

                new ReviewAddControllerRequest(
                        "NOT EXISTING USERNAME",
                        placeName,
                        ratings,
                        "This is test opinion about place"),

                new ReviewAddControllerRequest(
                        usernameOne,
                        "NOT EXISTING PLACE NAME",
                        ratings,
                        "This is test opinion about place")
        );

    }

    private static Stream<ReviewAddControllerRequest> provideValidRequest() {

        var ratings = new HashSet<StarRatingControllerDto>();

        ratings.add(new StarRatingControllerDto("Service",2));
        ratings.add(new StarRatingControllerDto("Equipment",4));
        ratings.add(new StarRatingControllerDto("Price",5));

        return Stream.of(

                new ReviewAddControllerRequest(
                        usernameOne,
                        placeName,
                        ratings,
                        "This is test opinion about place")
        );

    }

    @ParameterizedTest
    @MethodSource("provideTwoUsersSignUpRequestsForTests")
    void T01_Sign_Up_Users_Who_Add_Review(SignUpControllerRequest signUpControllerRequest){

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequest, HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("providePlaceForTests")
    void T02_Add_Place_That_Is_Reviewed(PlaceAddControllerRequest requestDto){

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyReviewsForTests")
    void T03_Add_Review_Empty_Values_Status_CONFLICT(ReviewAddControllerRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortReviewsForTests")
    void T04_Add_Review_TooLong_TooShort_Values_Status_CONFLICT(ReviewAddControllerRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideRequestNotExistingPlaceAndUser")
    void T05_Add_Review_Not_Existing_User_And_Place_Status_NOT_FOUND(ReviewAddControllerRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T06_Add_Valid_Review_Status_OK_Add_Entity(ReviewAddControllerRequest requestDto){

       ReviewValidator.getInstance().add(requestDto,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T06_Find_All_Reviews_By_Author_Status_OK_Correct_PlaceName(ReviewAddControllerRequest requestDto){

        List<String> actualPlaceNames = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),HttpStatus.OK)
                .stream().map(ReviewControllerResponse::getPlace)
                .forEach(actualPlaceNames::add);

        Assertions.assertTrue(actualPlaceNames.contains(requestDto.getPlace()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T07_Increment_Review_Likes_Status_OK(ReviewAddControllerRequest requestDto){

        List<ReviewControllerResponse> responses = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responses.forEach(x -> ReviewValidator
                .getInstance()
                .incrementReviewLikesById(x.getId(),HttpStatus.OK));

        List<ReviewControllerResponse> responsesAgain = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responsesAgain.forEach(x -> Assertions.assertEquals(1,x.getLikes()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T08_Increment_Review_Dislikes_Status_OK(ReviewAddControllerRequest requestDto){

        List<ReviewControllerResponse> responses = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responses.forEach(x -> ReviewValidator
                .getInstance()
                .incrementReviewDislikesById(x.getId(),HttpStatus.OK));

        List<ReviewControllerResponse> responsesAgain = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(), HttpStatus.OK));

        responsesAgain.forEach(x -> Assertions.assertEquals(1,x.getDislikes()));

    }



    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T09_Delete_All_Reviews_By_Id_Status_OK_Delete_Reviews(ReviewAddControllerRequest requestDto){

        List<Integer> ids = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),HttpStatus.OK)
                .stream().map(ReviewControllerResponse::getId)
                .forEach(ids::add);

        ids.forEach(x -> ReviewValidator.getInstance().deleteReviewById(x,HttpStatus.OK));

    }







    @ParameterizedTest
    @MethodSource("providePlaceForTests")
    void T41_Delete_Created_Place(PlaceAddControllerRequest requestDto){

        int id = PlaceValidator

                .getInstance()
                .findByName(requestDto.getName(),HttpStatus.OK)
                .getId();

        PlaceValidator.getInstance().deletePlaceById(id,HttpStatus.OK);

    }





    @ParameterizedTest
    @MethodSource("provideTwoUsersSignUpRequestsForTests")
    void T45_Delete_Created_Users(SignUpControllerRequest signUpControllerRequest){

        UserValidator
                .getInstance()
                .deleteUserByUsername(signUpControllerRequest.getUsername(),HttpStatus.OK);

    }





}
