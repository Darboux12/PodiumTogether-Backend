package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.PlaceRequestDto;
import com.podium.model.dto.other.OpeningDay;
import com.podium.model.dto.other.PlaceLocalization;
import com.podium.model.dto.other.Rating;
import com.podium.validator.PlaceValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class PlaceTest {

    private static PlaceRequestDto requestDto;
    private static String textValueHolder;
    private static int intValueHolder;
    private static double doubleValueHolder;

    @BeforeAll
    static void beforeClass(){

        TestLogger.setUp();

        PlaceLocalization placeLocalization = new PlaceLocalization(
                "PlaceTestCityName",
                "PlaceTestStreetName",
                123,
                "24-060",
                "Place test localization remarks"
        );

        List<OpeningDay> openingDays = new LinkedList<>();

        LocalTime timeFrom = LocalTime.parse("10:33:22");
        LocalTime timeTo = LocalTime.parse("17:00");

        openingDays.add(new OpeningDay("Monday",true,
                timeFrom,timeTo));
        openingDays.add(new OpeningDay("Tuesday",true,
                timeFrom,timeTo));
        openingDays.add(new OpeningDay("Wednesday",true,
                timeFrom,timeTo));
        openingDays.add(new OpeningDay("Thursday",true,
                timeFrom,timeTo));
        openingDays.add(new OpeningDay("Friday",true,
                timeFrom,timeTo));
        openingDays.add(new OpeningDay("Saturday",true,
                timeFrom,timeTo));
        openingDays.add(new OpeningDay("Sunday",true,
                timeFrom,timeTo));

        List<Rating> ratings = List.of(
                new Rating("Service",1),
                new Rating("Service",2),
                new Rating("Equipment",3),
                new Rating("Equipment",4),
                new Rating("Price",5)
        );

        requestDto = new PlaceRequestDto(
                "Test Place Name",
                "Football",
                placeLocalization,
                openingDays,
                50,
                PodiumLimits.minUsageTimeHours + 1,
                10,
                20,
                ratings,
                "This is test place review"
        );




    }

    private static Stream<String> provideEmptyValuesForTests(){
        return Stream.of("", " ", "  ","                    ");
    }

    private static Stream<Arguments> provideEmptyValuesAndDaysIndexesForTests(){

        return Stream.of(
                Arguments.of("",0),
                Arguments.of("",1),
                Arguments.of("",2),
                Arguments.of("",3),
                Arguments.of("",4),
                Arguments.of("",5),
                Arguments.of("",6),
                Arguments.of(" ",0),
                Arguments.of(" ",1),
                Arguments.of("  ",2),
                Arguments.of("  ",3),
                Arguments.of("      ",4),
                Arguments.of("  ",5),
                Arguments.of("      ",6)
        );




    }


    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T01_Add_Place_Empty_Name_Return_Status_CONFLICT(String emptyName){

        textValueHolder = requestDto.getName();
        requestDto.setName(emptyName);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.setName(textValueHolder);
    }

    @Test
    void T02_Add_Place_Too_Short_Name_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minPlaceNameLength - 1);
        textValueHolder = requestDto.getName();
        requestDto.setName(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setName(textValueHolder);

    }

    @Test
    void T03_Add_Place_Too_Long_Name_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxPlaceNameLength + 1);
        textValueHolder = requestDto.getName();
        requestDto.setName(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setName(textValueHolder);

    }

    @Test
    void T04_Add_Place_Too_Short_Discipline_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1);
        textValueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(textValueHolder);

    }

    @Test
    void T05_Add_Place_Too_Long_Discipline_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1);
        textValueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(textValueHolder);

    }

    @Test
    void T06_Add_Place_Too_Short_Localization_City_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minCityLength - 1);
        textValueHolder = requestDto.getPlaceLocalization().getCity();
        requestDto.getPlaceLocalization().setCity(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setCity(textValueHolder);

    }

    @Test
    void T07_Add_Place_Too_Long_Localization_City_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);
        textValueHolder = requestDto.getPlaceLocalization().getCity();
        requestDto.getPlaceLocalization().setCity(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setCity(textValueHolder);

    }

    @Test
    void T08_Add_Place_Too_Short_Localization_Street_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minStreetLength - 1);
        textValueHolder = requestDto.getPlaceLocalization().getStreet();
        requestDto.getPlaceLocalization().setStreet(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setStreet(textValueHolder);

    }

    @Test
    void T09_Add_Place_Too_Long_Localization_Street_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxStreetLength + 1);
        textValueHolder = requestDto.getPlaceLocalization().getStreet();
        requestDto.getPlaceLocalization().setStreet(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setStreet(textValueHolder);

    }

    @Test
    void T10_Add_Place_Too_Short_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toShort = PodiumLimits.minBuildingNumberLength - 1;
        intValueHolder = requestDto.getPlaceLocalization().getBuildingNumber();
        requestDto.getPlaceLocalization().setBuildingNumber(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setBuildingNumber(intValueHolder);

    }

    @Test
    void T11_Add_Place_Too_Long_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toLong = PodiumLimits.maxBuildingNumberLength + 1;
        intValueHolder = requestDto.getPlaceLocalization().getBuildingNumber();
        requestDto.getPlaceLocalization().setBuildingNumber(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setBuildingNumber(intValueHolder);

    }

    @Test
    void T12_Add_Place_Empty_Localization_Street_Return_Status_OK_As_Its_Optional(){

        textValueHolder = requestDto.getPlaceLocalization().getStreet();
        requestDto.getPlaceLocalization().setStreet("");

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getPlaceLocalization().setStreet(textValueHolder);

    }

    @Test
    void T13_Add_Place_Empty_Localization_BuildingNumber_Return_Status_OK_As_Its_Optional(){

        intValueHolder = requestDto.getPlaceLocalization().getBuildingNumber();
        requestDto.getPlaceLocalization().setBuildingNumber(0);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getPlaceLocalization().setBuildingNumber(intValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T14_Add_Place_Empty_Localization_PostalCode_Return_Status_OK_As_Its_Optional(String emptyPostal){

        textValueHolder = requestDto.getPlaceLocalization().getPostalCode();
        requestDto.getPlaceLocalization().setPostalCode(emptyPostal);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getPlaceLocalization().setPostalCode(textValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T15_Add_Place_Empty_Localization_Remarks_Return_Status_OK_As_Its_Optional(String emptyRemarks){

        textValueHolder = requestDto.getPlaceLocalization().getLocalizationRemarks();
        requestDto.getPlaceLocalization().setLocalizationRemarks(emptyRemarks);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getPlaceLocalization().setLocalizationRemarks(textValueHolder);

    }

    @Test
    void T16_Add_Place_Too_Short_Localization_PostalCode_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minPostalLength - 1);
        textValueHolder = requestDto.getPlaceLocalization().getPostalCode();
        requestDto.getPlaceLocalization().setPostalCode(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setPostalCode(textValueHolder);

    }

    @Test
    void T17_Add_Place_Too_Long_Localization_PostalCode_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxPostalLength + 1);
        textValueHolder = requestDto.getPlaceLocalization().getPostalCode();
        requestDto.getPlaceLocalization().setPostalCode(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setPostalCode(textValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T18_Add_Place_Empty_Discipline_Return_Status_CONFLICT(String emptyDiscipline){

        textValueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(emptyDiscipline);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.setDiscipline(textValueHolder);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T19_Add_Place_Empty_Localization_City_Return_Status_CONFLICT(String emptyCity){

        textValueHolder = requestDto.getPlaceLocalization().getCity();
        requestDto.getPlaceLocalization().setCity(emptyCity);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setCity(textValueHolder);
    }

    @Test
    void T20_Add_Place_Too_Short_Localization_Remarks_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minLocalizationRemarksLength - 1);
        textValueHolder = requestDto.getPlaceLocalization().getLocalizationRemarks();
        requestDto.getPlaceLocalization().setLocalizationRemarks(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setLocalizationRemarks(textValueHolder);

    }

    @Test
    void T21_Add_Place_Too_Long_Localization_Remarks_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxLocalizationRemarksLength + 1);
        textValueHolder = requestDto.getPlaceLocalization().getLocalizationRemarks();
        requestDto.getPlaceLocalization().setLocalizationRemarks(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setLocalizationRemarks(textValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesAndDaysIndexesForTests")
    void T22_Add_Place_Empty_OpeningDay_Day_Return_Status_CONFLICT(String emptyDay, int index){

        textValueHolder = requestDto.getOpeningDays().get(index).getDay();
        requestDto.getOpeningDays().get(index).setDay(emptyDay);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getOpeningDays().get(index).setDay(textValueHolder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6})
    void T23_Add_Place_Wrong_OpeningDay_Day_Return_Status_CONFLICT(int index){

        textValueHolder = requestDto.getOpeningDays().get( index).getDay();
        requestDto.getOpeningDays().get( index).setDay("WrongWeekDay");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getOpeningDays().get(index).setDay(textValueHolder);
    }

    @Test
    void T24_Add_Place_ToShortList_OpeningDays_Return_Status_CONFLICT(){

        OpeningDay openingDay = requestDto.getOpeningDays().get(0);
        requestDto.getOpeningDays().remove(0);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getOpeningDays().add(openingDay);
    }

    @Test
    void T25_Add_Place_Too_Small_Cost_Return_Status_CONFLICT(){

        doubleValueHolder = requestDto.getCost();
        requestDto.setCost(PodiumLimits.minCost - 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setCost(doubleValueHolder );

    }

    @Test
    void T26_Add_Place_Too_Large_Cost_Return_Status_CONFLICT(){

        doubleValueHolder = requestDto.getCost();
        requestDto.setCost(PodiumLimits.maxCost + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setCost(doubleValueHolder );

    }

    @Test
    void T27_Add_Place_Too_Small_usageTime_Return_Status_CONFLICT(){

        doubleValueHolder = requestDto.getUsageTime();
        requestDto.setUsageTime(PodiumLimits.minUsageTimeHours - 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setUsageTime(doubleValueHolder );

    }

    @Test
    void T28_Add_Place_Too_Large_usageTime_Return_Status_CONFLICT(){

        doubleValueHolder = requestDto.getUsageTime();
        requestDto.setUsageTime(PodiumLimits.maxUsageTimeHours + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setUsageTime(doubleValueHolder);

    }

    @Test
    void T29_Add_Place_Too_Small_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMinAge();
        requestDto.setMinAge(PodiumLimits.minPlaceMinAge - 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMinAge(intValueHolder);

    }

    @Test
    void T29_Add_Place_Too_Large_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMinAge();
        requestDto.setMinAge(PodiumLimits.maxPlaceMinAge + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMinAge(intValueHolder);

    }

    @Test
    void T30_Add_Place_Too_Small_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMaxAge();
        requestDto.setMaxAge(PodiumLimits.minPlaceMaxAge - 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(intValueHolder);

    }

    @Test
    void T31_Add_Place_Too_Large_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMaxAge();
        requestDto.setMaxAge(PodiumLimits.maxPlaceMaxAge + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(intValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T32_Add_Place_Empty_Rating_Category_Return_Status_CONFLICT(String emptyCategory){

        textValueHolder = requestDto.getRatings().get(0).getCategory();
        requestDto.getRatings().get(0).setCategory(emptyCategory);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getRatings().get(0).setCategory(textValueHolder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4})
    void T33_Add_Place_Too_Small_Rating_Return_Status_CONFLICT(int index){

        intValueHolder = requestDto.getRatings().get(index).getRating();
        requestDto.getRatings().get(index).setRating(PodiumLimits.minPlaceRating - 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getRatings().get(index).setRating(intValueHolder);

    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4})
    void T34_Add_Place_Too_Large_Rating_Return_Status_CONFLICT(int index){

        intValueHolder = requestDto.getRatings().get(index).getRating();
        requestDto.getRatings().get(index).setRating(PodiumLimits.maxPlaceRating + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getRatings().get(index).setRating(intValueHolder);

    }

    @Test
    void T35_Add_Place_ToShort_Review_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minPlaceReview - 1);

        textValueHolder = requestDto.getReview();
        requestDto.setReview(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setReview(textValueHolder);

    }

    @Test
    void T36_Add_Place_To_Large_Localization_Remarks_Return_CONFLICT(){


        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxPlaceReview + 1);

        textValueHolder = requestDto.getReview();
        requestDto.setReview(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setReview(textValueHolder);

    }

    @ParameterizedTest
    @ValueSource(strings = {"DISCIPLINE_ONE","DISCIPLINE_TWO"})
    void T36_Add_Place_Non_Existing_Discipline_Return_Status_NOTFOUND(String discipline){

        textValueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(discipline);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.NOT_FOUND);

        requestDto.setDiscipline(textValueHolder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6})
    void T37_Add_Place_OpeningDay_OpeningTimeFrom_After_OpeningTimeTo_Return_Status_CONFLICT(int index){

       LocalTime timeFrom  = requestDto.getOpeningDays().get(index).getOpeningTimeFrom();
        requestDto.getOpeningDays().get(index).setOpeningTimeFrom(LocalTime.parse("18:00:00"));

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getOpeningDays().get(index).setOpeningTimeFrom(timeFrom);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4})
    void T38_Add_Place_Non_Existing_Rating_Category_Return_Status_NOTFOUND(int index){

        textValueHolder = requestDto.getRatings().get(index).getCategory();
        requestDto.getRatings().get(index).setCategory("WRONG_CATEGORY");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.NOT_FOUND);

        requestDto.getRatings().get(index).setCategory(textValueHolder);
    }

}
