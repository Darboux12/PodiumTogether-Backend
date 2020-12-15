package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.PlaceAddRequest;
import com.podium.controller.dto.other.BusinessDayDto;
import com.podium.controller.dto.other.LocalizationDto;
import com.podium.controller.dto.other.RatingDto;
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

    private static PlaceAddRequest requestDto;
    private static String textValueHolder;
    private static int intValueHolder;
    private static double doubleValueHolder;

    @BeforeAll
    static void beforeClass(){

        TestLogger.setUp();

       LocalizationDto localizationDto = new LocalizationDto(
                "PlaceTestCityName",
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

        requestDto = new PlaceAddRequest(
                "Test Place Name",
                "Football",
                localizationDto,
                businessDayDtos,
                50,
                PodiumLimits.minUsageTimeHours + 1,
                10,
                20
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
        textValueHolder = requestDto.getLocalizationDto().getCity();
        requestDto.getLocalizationDto().setCity(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setCity(textValueHolder);

    }

    @Test
    void T07_Add_Place_Too_Long_Localization_City_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);
        textValueHolder = requestDto.getLocalizationDto().getCity();
        requestDto.getLocalizationDto().setCity(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setCity(textValueHolder);

    }

    @Test
    void T08_Add_Place_Too_Short_Localization_Street_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minStreetLength - 1);
        textValueHolder = requestDto.getLocalizationDto().getStreet();
        requestDto.getLocalizationDto().setStreet(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setStreet(textValueHolder);

    }

    @Test
    void T09_Add_Place_Too_Long_Localization_Street_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxStreetLength + 1);
        textValueHolder = requestDto.getLocalizationDto().getStreet();
        requestDto.getLocalizationDto().setStreet(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setStreet(textValueHolder);

    }

    @Test
    void T10_Add_Place_Too_Short_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toShort = PodiumLimits.minBuildingNumberLength - 1;
        intValueHolder = requestDto.getLocalizationDto().getBuildingNumber();
        requestDto.getLocalizationDto().setBuildingNumber(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setBuildingNumber(intValueHolder);

    }

    @Test
    void T11_Add_Place_Too_Long_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toLong = PodiumLimits.maxBuildingNumberLength + 1;
        intValueHolder = requestDto.getLocalizationDto().getBuildingNumber();
        requestDto.getLocalizationDto().setBuildingNumber(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setBuildingNumber(intValueHolder);

    }

    @Test
    void T12_Add_Place_Empty_Localization_Street_Return_Status_OK_As_Its_Optional(){

        textValueHolder = requestDto.getLocalizationDto().getStreet();
        requestDto.getLocalizationDto().setStreet("");

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getLocalizationDto().setStreet(textValueHolder);

    }

    @Test
    void T13_Add_Place_Empty_Localization_BuildingNumber_Return_Status_OK_As_Its_Optional(){

        intValueHolder = requestDto.getLocalizationDto().getBuildingNumber();
        requestDto.getLocalizationDto().setBuildingNumber(0);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getLocalizationDto().setBuildingNumber(intValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T14_Add_Place_Empty_Localization_PostalCode_Return_Status_OK_As_Its_Optional(String emptyPostal){

        textValueHolder = requestDto.getLocalizationDto().getPostalCode();
        requestDto.getLocalizationDto().setPostalCode(emptyPostal);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getLocalizationDto().setPostalCode(textValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T15_Add_Place_Empty_Localization_Remarks_Return_Status_OK_As_Its_Optional(String emptyRemarks){

        textValueHolder = requestDto.getLocalizationDto().getLocalizationRemarks();
        requestDto.getLocalizationDto().setLocalizationRemarks(emptyRemarks);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

        requestDto.getLocalizationDto().setLocalizationRemarks(textValueHolder);

    }

    @Test
    void T16_Add_Place_Too_Short_Localization_PostalCode_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minPostalLength - 1);
        textValueHolder = requestDto.getLocalizationDto().getPostalCode();
        requestDto.getLocalizationDto().setPostalCode(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setPostalCode(textValueHolder);

    }

    @Test
    void T17_Add_Place_Too_Long_Localization_PostalCode_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxPostalLength + 1);
        textValueHolder = requestDto.getLocalizationDto().getPostalCode();
        requestDto.getLocalizationDto().setPostalCode(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setPostalCode(textValueHolder);

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

        textValueHolder = requestDto.getLocalizationDto().getCity();
        requestDto.getLocalizationDto().setCity(emptyCity);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setCity(textValueHolder);
    }

    @Test
    void T20_Add_Place_Too_Short_Localization_Remarks_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minLocalizationRemarksLength - 1);
        textValueHolder = requestDto.getLocalizationDto().getLocalizationRemarks();
        requestDto.getLocalizationDto().setLocalizationRemarks(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setLocalizationRemarks(textValueHolder);

    }

    @Test
    void T21_Add_Place_Too_Long_Localization_Remarks_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxLocalizationRemarksLength + 1);
        textValueHolder = requestDto.getLocalizationDto().getLocalizationRemarks();
        requestDto.getLocalizationDto().setLocalizationRemarks(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationDto().setLocalizationRemarks(textValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesAndDaysIndexesForTests")
    void T22_Add_Place_Empty_OpeningDay_Day_Return_Status_CONFLICT(String emptyDay, int index){

        textValueHolder = requestDto.getBusinessDayDtos().get(index).getDay();
        requestDto.getBusinessDayDtos().get(index).setDay(emptyDay);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayDtos().get(index).setDay(textValueHolder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6})
    void T23_Add_Place_Wrong_OpeningDay_Day_Return_Status_CONFLICT(int index){

        textValueHolder = requestDto.getBusinessDayDtos().get( index).getDay();
        requestDto.getBusinessDayDtos().get( index).setDay("WrongWeekDay");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayDtos().get(index).setDay(textValueHolder);
    }

    @Test
    void T24_Add_Place_ToShortList_OpeningDays_Return_Status_CONFLICT(){

        BusinessDayDto businessDayDto = requestDto.getBusinessDayDtos().get(0);
        requestDto.getBusinessDayDtos().remove(0);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayDtos().add(businessDayDto);
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

       LocalTime timeFrom  = requestDto.getBusinessDayDtos().get(index).getOpeningTimeFrom();
        requestDto.getBusinessDayDtos().get(index).setOpeningTimeFrom(LocalTime.parse("18:00:00"));

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayDtos().get(index).setOpeningTimeFrom(timeFrom);
    }

    @Test
    void T38_Add_Valid_Place_Should_Return_Status_OK(){

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.OK);

    }


    @Test
    void T40_Delete_Created_Place_Should_Return_Status_OK_Delete_Entity(){






    }

}
