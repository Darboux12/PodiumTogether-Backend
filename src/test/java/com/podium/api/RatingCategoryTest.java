package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.RatingCategoryAddControllerRequest;
import com.podium.controller.dto.response.RatingCategoryControllerResponse;
import com.podium.validator.RatingCategoryValidator;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class RatingCategoryTest {

    private static String adminToken = "";
    private static String subscriberToken = "";

    private static Stream<String> provideCategoryNamesForTests(){

        return Stream.of(
                "CATEGORY_NAME_ONE",
                "CATEGORY_NAME_TWO",
                "CATEGORY_NAME_THREE",
                "CATEGORY_NAME_FOUR",
                "CATEGORY_NAME_FIVE"
        );

    }

    private static Stream<String> provideEmptyCategoryNamesForTests(){

        return Stream.of("", "   ", "  ");

    }

    private static Stream<String> provideTooLongAndTooShortCategoryNamesForTests(){

        return Stream.of(
                StringUtils.repeat("*", PodiumLimits.maxRatingCategoryLength + 1),
                StringUtils.repeat("*", PodiumLimits.maxRatingCategoryLength + 10),
                StringUtils.repeat("*", PodiumLimits.maxRatingCategoryLength + 100),
                StringUtils.repeat("*", PodiumLimits.minRatingCategoryLength - 1),
                StringUtils.repeat("*", PodiumLimits.minRatingCategoryLength - 10),
                StringUtils.repeat("*", PodiumLimits.minRatingCategoryLength - 100)
        );

    }

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
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
    @MethodSource("provideCategoryNamesForTests")
    void T03_Add_Valid_Category_Should_Return_Status_OK(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryAddControllerRequest(category),adminToken, HttpStatus.OK);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CATEGORY_NAME_ONE","CATEGORY_NAME_TWO"})
    void T04_Add_Same_Category_Again_Should_Return_Status_CONFLICT(String category){
        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryAddControllerRequest(category),adminToken,HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCategoryNamesForTests")
    void T05_Add_Empty_Category_Should_Return_Status_CONFLICT(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryAddControllerRequest(category),adminToken,HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortCategoryNamesForTests")
    void T06_Add_Too_Long_And_Too_Short_Category_Should_Return_Status_CONFLICT(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryAddControllerRequest(category),adminToken,HttpStatus.CONFLICT);
    }

    @Test
    void T07_Find_All_Category_Should_Return_Status_OK_Containing_Added_Cities(){

        List<String> responseCities = RatingCategoryValidator
                .getInstance()
                .findAll(adminToken,HttpStatus.OK)
                .stream()
                .map(RatingCategoryControllerResponse::getCategory)
                .collect(Collectors.toList());

        Assertions.assertTrue
                (responseCities.containsAll
                        (provideCategoryNamesForTests().collect(Collectors.toList())));

    }

    @ParameterizedTest
    @ValueSource(strings = {"CATEGORY_NAME_ONE","CATEGORY_NAME_TWO"})
    void T08_Find_Category_By_Name_Should_Return_Status_OK_And_Added_Category(String category){

        String categoryName =

                RatingCategoryValidator
                        .getInstance()
                        .findByCategory(category,adminToken,HttpStatus.OK)
                        .getCategory();

        Assertions.assertEquals(category,categoryName);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_NOT_EXISTING_CATEGORY"})
    void T09_Find_Category_By_Name_Not_Exist_Should_Return_Status_NOT_FOUND(String category){

        RatingCategoryValidator
                .getInstance()
                .findByCategory(category,adminToken,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @ValueSource(strings = {"CATEGORY_NAME_ONE","CATEGORY_NAME_TWO"})
    void T10_Exist_Category_By_Name_Should_Return_TRUE(String category){

        boolean isPresent =

        RatingCategoryValidator
                .getInstance()
                .existCategory(category,adminToken,HttpStatus.OK);

        Assertions.assertTrue(isPresent);

    }

    @ParameterizedTest
    @MethodSource("provideCategoryNamesForTests")
    void T11_Delete_Category_Should_Return_Status_OK_Deleting_Added_Cities(String category){

        RatingCategoryValidator
                .getInstance()
                .deleteCategory(category,adminToken,HttpStatus.OK);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T12_Exist_Deleted_Category_By_Name_Should_Return_FALSE(String category){

        boolean isPresent =

        RatingCategoryValidator
                .getInstance()
                .existCategory(category,adminToken,HttpStatus.OK);

        Assertions.assertFalse(isPresent);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T13_Delete_Created_Category_Again_Should_Return_Status_NOTFOUND(String category){

        RatingCategoryValidator
                .getInstance()
                .deleteCategory(category,adminToken,HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @MethodSource("provideCategoryNamesForTests")
    void T14_Add_Category_As_Subscriber_Should_Return_UNAUTHORIZED(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryAddControllerRequest(category),subscriberToken, HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @MethodSource("provideCategoryNamesForTests")
    void T15_Delete_Category_As_Subscriber_Should_Return_UNAUTHORIZED(String category){

        RatingCategoryValidator
                .getInstance()
                .deleteCategory(category,subscriberToken,HttpStatus.UNAUTHORIZED);

    }

}
