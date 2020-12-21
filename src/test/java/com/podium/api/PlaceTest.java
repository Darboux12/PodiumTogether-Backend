package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.PlaceAddControllerRequest;
import com.podium.controller.dto.other.BusinessDayControllerDto;
import com.podium.controller.dto.other.LocalizationControllerDto;
import com.podium.validator.PlaceValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
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

    private static PlaceAddControllerRequest requestDto;
    private static String textValueHolder;
    private static int intValueHolder;
    private static double doubleValueHolder;

    @BeforeAll
    static void beforeClass(){

        TestLogger.setUp();

       LocalizationControllerDto localizationControllerDto = new LocalizationControllerDto(
                "PlaceTestCityName",
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

        requestDto = new PlaceAddControllerRequest(
                "Test Place Name",
                "Football",
                localizationControllerDto,
                businessDayControllerDtos,
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
        textValueHolder = requestDto.getLocalizationControllerDto().getCity();
        requestDto.getLocalizationControllerDto().setCity(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setCity(textValueHolder);

    }

    @Test
    void T07_Add_Place_Too_Long_Localization_City_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getCity();
        requestDto.getLocalizationControllerDto().setCity(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setCity(textValueHolder);

    }

    @Test
    void T08_Add_Place_Too_Short_Localization_Street_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minStreetLength - 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getStreet();
        requestDto.getLocalizationControllerDto().setStreet(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setStreet(textValueHolder);

    }

    @Test
    void T09_Add_Place_Too_Long_Localization_Street_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxStreetLength + 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getStreet();
        requestDto.getLocalizationControllerDto().setStreet(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setStreet(textValueHolder);

    }

    @Test
    void T10_Add_Place_Too_Short_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toShort = PodiumLimits.minBuildingNumberLength - 1;
        intValueHolder = requestDto.getLocalizationControllerDto().getBuildingNumber();
        requestDto.getLocalizationControllerDto().setBuildingNumber(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setBuildingNumber(intValueHolder);

    }

    @Test
    void T11_Add_Place_Too_Long_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toLong = PodiumLimits.maxBuildingNumberLength + 1;
        intValueHolder = requestDto.getLocalizationControllerDto().getBuildingNumber();
        requestDto.getLocalizationControllerDto().setBuildingNumber(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setBuildingNumber(intValueHolder);

    }

    @Test
    void T12_Add_Place_Empty_Localization_Street_Return_Status_CONFLICTl(){

        textValueHolder = requestDto.getLocalizationControllerDto().getStreet();
        requestDto.getLocalizationControllerDto().setStreet("");

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setStreet(textValueHolder);

    }

    @Test
    void T13_Add_Place_Empty_Localization_BuildingNumber_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getLocalizationControllerDto().getBuildingNumber();
        requestDto.getLocalizationControllerDto().setBuildingNumber(0);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setBuildingNumber(intValueHolder);

    }

    @Test
    void T14_Add_Place_Empty_Localization_PostalCode_Return_Status_CONFLICT(){

        textValueHolder = requestDto.getLocalizationControllerDto().getPostalCode();
        requestDto.getLocalizationControllerDto().setPostalCode("");

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setPostalCode(textValueHolder);

    }

    @Test
    void T15_Add_Place_Empty_Localization_Remarks_Return_Status_CONFLICTl(){

        textValueHolder = requestDto.getLocalizationControllerDto().getLocalizationRemarks();
        requestDto.getLocalizationControllerDto().setLocalizationRemarks("");

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setLocalizationRemarks(textValueHolder);

    }

    @Test
    void T16_Add_Place_Too_Short_Localization_PostalCode_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minPostalLength - 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getPostalCode();
        requestDto.getLocalizationControllerDto().setPostalCode(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setPostalCode(textValueHolder);

    }

    @Test
    void T17_Add_Place_Too_Long_Localization_PostalCode_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxPostalLength + 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getPostalCode();
        requestDto.getLocalizationControllerDto().setPostalCode(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setPostalCode(textValueHolder);

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

        textValueHolder = requestDto.getLocalizationControllerDto().getCity();
        requestDto.getLocalizationControllerDto().setCity(emptyCity);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setCity(textValueHolder);
    }

    @Test
    void T20_Add_Place_Too_Short_Localization_Remarks_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minLocalizationRemarksLength - 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getLocalizationRemarks();
        requestDto.getLocalizationControllerDto().setLocalizationRemarks(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setLocalizationRemarks(textValueHolder);

    }

    @Test
    void T21_Add_Place_Too_Long_Localization_Remarks_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxLocalizationRemarksLength + 1);
        textValueHolder = requestDto.getLocalizationControllerDto().getLocalizationRemarks();
        requestDto.getLocalizationControllerDto().setLocalizationRemarks(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getLocalizationControllerDto().setLocalizationRemarks(textValueHolder);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesAndDaysIndexesForTests")
    void T22_Add_Place_Empty_OpeningDay_Day_Return_Status_CONFLICT(String emptyDay, int index){

        textValueHolder = requestDto.getBusinessDayControllerDtos().get(index).getDay();
        requestDto.getBusinessDayControllerDtos().get(index).setDay(emptyDay);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayControllerDtos().get(index).setDay(textValueHolder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6})
    void T23_Add_Place_Wrong_OpeningDay_Day_Return_Status_CONFLICT(int index){

        textValueHolder = requestDto.getBusinessDayControllerDtos().get( index).getDay();
        requestDto.getBusinessDayControllerDtos().get( index).setDay("WrongWeekDay");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayControllerDtos().get(index).setDay(textValueHolder);
    }

    @Test
    void T24_Add_Place_ToShortList_OpeningDays_Return_Status_CONFLICT(){

        BusinessDayControllerDto businessDayControllerDto = requestDto.getBusinessDayControllerDtos().get(0);
        requestDto.getBusinessDayControllerDtos().remove(0);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayControllerDtos().add(businessDayControllerDto);
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
    void T30_Add_Place_Too_Large_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMinAge();
        requestDto.setMinAge(PodiumLimits.maxPlaceMinAge + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMinAge(intValueHolder);

    }

    @Test
    void T31_Add_Place_Too_Small_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMaxAge();
        requestDto.setMaxAge(PodiumLimits.minPlaceMaxAge - 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(intValueHolder);

    }

    @Test
    void T32_Add_Place_Too_Large_MinAge_Return_Status_CONFLICT(){

        intValueHolder = requestDto.getMaxAge();
        requestDto.setMaxAge(PodiumLimits.maxPlaceMaxAge + 1);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setMaxAge(intValueHolder);

    }

    @ParameterizedTest
    @ValueSource(strings = {"DISCIPLINE_ONE","DISCIPLINE_TWO"})
    void T33_Add_Place_Non_Existing_Discipline_Return_Status_NOTFOUND(String discipline){

        textValueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(discipline);

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.NOT_FOUND);

        requestDto.setDiscipline(textValueHolder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6})
    void T34_Add_Place_OpeningDay_OpeningTimeFrom_After_OpeningTimeTo_Return_Status_CONFLICT(int index){

       LocalTime timeFrom  = requestDto.getBusinessDayControllerDtos().get(index).getOpeningTimeFrom();
        requestDto.getBusinessDayControllerDtos().get(index).setOpeningTimeFrom(LocalTime.parse("18:00:00"));

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getBusinessDayControllerDtos().get(index).setOpeningTimeFrom(timeFrom);
    }

    @Test
    void T35_Add_Valid_Place_Should_Return_Status_OK(){

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.OK);

    }

    @Test
    void T36_Find_Created_Place_Return_Status_OK_Find_Entity(){

        String actualName = PlaceValidator

                .getInstance()
                .findByName(requestDto.getName(),HttpStatus.OK)
                .getName();

        Assertions.assertEquals(requestDto.getName(),actualName);

    }

    @Test
    void T37_Delete_Created_Place_Should_Return_Status_OK_Delete_Entity(){

        int id = PlaceValidator

                .getInstance()
                .findByName(requestDto.getName(),HttpStatus.OK)
                .getId();

        PlaceValidator.getInstance().deletePlaceById(id,HttpStatus.OK);

    }

    @Test
    void T38_Find_Deleted_Place_Return_Status_NOT_FOUND(){

        PlaceValidator
                .getInstance()
                .findByName(requestDto.getName(),HttpStatus.NOT_FOUND);

    }

}
