package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.other.*;
import com.podium.controller.dto.request.JwtControllerRequest;
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

    private static String passwordOne = "TEST PASSWORD ONE";
    private static String passwordTwo = "TEST PASSWORD TWO";

    private static String placeName = "Test Place Name";

    private static String adminToken = "";
    private static String subscriberToken = "";

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    private static Stream<ReviewAddControllerRequest> provideEmptyReviewsForTests() {

        var ratings = new HashSet<StarRatingControllerDto>();

        ratings.add(new StarRatingControllerDto("Service",2));
        ratings.add(new StarRatingControllerDto("Equipment",4));
        ratings.add(new StarRatingControllerDto("Price",5));
        ratings.add(new StarRatingControllerDto("Opening Hours",1));

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
        ratings.add(new StarRatingControllerDto("Opening Hours",1));

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
        ratings.add(new StarRatingControllerDto("Opening Hours",1));

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
        ratings.add(new StarRatingControllerDto("Opening Hours",5));

        return Stream.of(

                new ReviewAddControllerRequest(
                        usernameOne,
                        placeName,
                        ratings,
                        "This is test opinion about place")
        );

    }

    @Test
    void T01_Sign_In_As_Admin_User_Get_Token(){

        adminToken =

                UserValidator
                        .getInstance()
                        .signIn(new JwtControllerRequest("admin","adminadmin"),HttpStatus.OK)
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
    @MethodSource("provideEmptyReviewsForTests")
    void T03_Add_Review_Empty_Values_Status_CONFLICT(ReviewAddControllerRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,subscriberToken,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortReviewsForTests")
    void T04_Add_Review_TooLong_TooShort_Values_Status_CONFLICT(ReviewAddControllerRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,subscriberToken,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideRequestNotExistingPlaceAndUser")
    void T05_Add_Review_Not_Existing_User_And_Place_Status_NOT_FOUND(ReviewAddControllerRequest requestDto){

        ReviewValidator.getInstance().add(requestDto,subscriberToken,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T06_Add_Valid_Review_Status_OK_Add_Entity(ReviewAddControllerRequest requestDto){

       ReviewValidator.getInstance().add(requestDto,subscriberToken,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T07_Find_All_Reviews_By_Author_Status_OK_Correct_PlaceName(ReviewAddControllerRequest requestDto){

        List<String> actualPlaceNames = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),subscriberToken,HttpStatus.OK)
                .stream().map(ReviewControllerResponse::getPlace)
                .forEach(actualPlaceNames::add);

        Assertions.assertTrue(actualPlaceNames.contains(requestDto.getPlace()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T08_Increment_Review_Likes_Status_OK(ReviewAddControllerRequest requestDto){

        List<ReviewControllerResponse> responses = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),subscriberToken,HttpStatus.OK));

        responses.forEach(x -> ReviewValidator
                .getInstance()
                .incrementReviewLikesById(x.getId(),subscriberToken,HttpStatus.OK));

        List<ReviewControllerResponse> responsesAgain = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),subscriberToken,HttpStatus.OK));

        responsesAgain.forEach(x -> Assertions.assertEquals(1,x.getLikes()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T09_Increment_Review_Dislikes_Status_OK(ReviewAddControllerRequest requestDto){

        List<ReviewControllerResponse> responses = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),subscriberToken,HttpStatus.OK));

        responses.forEach(x -> ReviewValidator
                .getInstance()
                .incrementReviewDislikesById(x.getId(),subscriberToken,HttpStatus.OK));

        List<ReviewControllerResponse> responsesAgain = new ArrayList<>(ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),subscriberToken,HttpStatus.OK));

        responsesAgain.forEach(x -> Assertions.assertEquals(1,x.getDislikes()));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T10_Delete_All_Reviews_As_Subscriber_Status_UNAUTHORIZED(ReviewAddControllerRequest requestDto){

        List<Integer> ids = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),subscriberToken,HttpStatus.OK)
                .stream().map(ReviewControllerResponse::getId)
                .forEach(ids::add);

        ids.forEach(x -> ReviewValidator.getInstance().deleteReviewById(x,subscriberToken,HttpStatus.UNAUTHORIZED));

    }

    @ParameterizedTest
    @MethodSource("provideValidRequest")
    void T11_Delete_All_Reviews_By_Id_Status_OK_Delete_Reviews(ReviewAddControllerRequest requestDto){

        List<Integer> ids = new ArrayList<>();

        ReviewValidator
                .getInstance()
                .findAllReviewsByAuthor(requestDto.getAuthor(),adminToken,HttpStatus.OK)
                .stream().map(ReviewControllerResponse::getId)
                .forEach(ids::add);

        ids.forEach(x -> ReviewValidator.getInstance().deleteReviewById(x,adminToken,HttpStatus.OK));

    }

}
