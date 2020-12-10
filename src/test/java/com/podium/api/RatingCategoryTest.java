package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.validator.RatingCategoryValidator;
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

    @ParameterizedTest
    @MethodSource("provideCategoryNamesForTests")
    void T01_Add_Valid_Category_Should_Return_Status_OK(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryRequestDto(category), HttpStatus.OK);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CATEGORY_NAME_ONE","CATEGORY_NAME_TWO"})
    void T02_Add_Same_Category_Again_Should_Return_Status_CONFLICT(String category){
        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryRequestDto(category),HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCategoryNamesForTests")
    void T03_Add_Empty_Category_Should_Return_Status_CONFLICT(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryRequestDto(category),HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortCategoryNamesForTests")
    void T04_Add_Too_Long_And_Too_Short_Category_Should_Return_Status_CONFLICT(String category){

        RatingCategoryValidator
                .getInstance()
                .add(new RatingCategoryRequestDto(category),HttpStatus.CONFLICT);
    }

    @Test
    void T06_Find_All_Category_Should_Return_Status_OK_Containing_Added_Cities(){

        List<String> responseCities = RatingCategoryValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(RatingCategoryResponseDto::getCategory)
                .collect(Collectors.toList());

        Assertions.assertTrue
                (responseCities.containsAll
                        (provideCategoryNamesForTests().collect(Collectors.toList())));

    }

    @ParameterizedTest
    @ValueSource(strings = {"CATEGORY_NAME_ONE","CATEGORY_NAME_TWO"})
    void T07_Find_Category_By_Name_Should_Return_Status_OK_And_Added_Category(String category){

        String categoryName =

                RatingCategoryValidator
                        .getInstance()
                        .findByCategory(category,HttpStatus.OK)
                        .getCategory();

        Assertions.assertEquals(category,categoryName);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_NOT_EXISTING_CATEGORY"})
    void T10_Find_Category_By_Name_Not_Exist_Should_Return_Status_NOT_FOUND(String category){

        RatingCategoryValidator
                .getInstance()
                .findByCategory(category,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @ValueSource(strings = {"CATEGORY_NAME_ONE","CATEGORY_NAME_TWO"})
    void T11_Exist_Category_By_Name_Should_Return_TRUE(String category){

        boolean isPresent =

        RatingCategoryValidator
                .getInstance()
                .existCategory(category,HttpStatus.OK);

        Assertions.assertTrue(isPresent);

    }

    @ParameterizedTest
    @MethodSource("provideCategoryNamesForTests")
    void T12_Delete_Category_Should_Return_Status_OK_Deleting_Added_Cities(String category){

        RatingCategoryValidator
                .getInstance()
                .deleteCategory(category,HttpStatus.OK);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T13_Exist_Deleted_Category_By_Name_Should_Return_FALSE(String category){

        boolean isPresent =

        RatingCategoryValidator
                .getInstance()
                .existCategory(category,HttpStatus.OK);

        Assertions.assertFalse(isPresent);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T14_Delete_Created_Category_Again_Should_Return_Status_NOTFOUND(String category){

        RatingCategoryValidator
                .getInstance()
                .deleteCategory(category,HttpStatus.NOT_FOUND);
    }

}