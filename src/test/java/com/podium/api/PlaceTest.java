package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.request.PlaceRequestDto;
import com.podium.model.dto.other.OpeningDay;
import com.podium.model.dto.other.PlaceLocalization;
import com.podium.model.dto.other.Rating;
import com.podium.validator.CityValidator;
import com.podium.validator.PlaceValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlaceTest {

    private static PlaceRequestDto requestDto;
    private static String textValueHolder;
    private static int intValueHolder;

    @BeforeClass
    public static void beforeClass(){

        TestLogger.setUp();

        PlaceLocalization placeLocalization = new PlaceLocalization(
                "PlaceTestCityName",
                "PlaceTestStreetName",
                123,
                "24-060",
                "Place test localization remarks"
        );

        List<OpeningDay> openingDays = List.of(
                new OpeningDay("Monday",true,true,
                        "10:00","17:00"),
                new OpeningDay("Tuesday",true,true,
                        "10:00","17:00"),
                new OpeningDay("Wednesday",true,true,
                        "10:00","17:00"),
                new OpeningDay("Thursday",true,true,
                        "10:00","17:00"),
                new OpeningDay("Friday",true,true,
                        "10:00","17:00"),
                new OpeningDay("Saturday",true,true,
                        "10:00","17:00"),
                new OpeningDay("Sunday",true,true,
                        "10:00","17:00")
        );

        List<Rating> ratings = List.of(new Rating("Service",5));



        requestDto = new PlaceRequestDto(
                "Test Place Name",
                "Football",
                placeLocalization,
                openingDays,
                50,
                2.5,
                10,
                20,
                ratings,
                "This is test place review"
        );




    }

    @Test
    public void T01_Add_Place_Empty_Name_Return_Status_CONFLICT(){

        textValueHolder = requestDto.getName();
        requestDto.setName("");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.setName(textValueHolder);
    }

    @Test
    public void T02_Add_Place_Too_Short_Name_Return_Status_CONFLICT(){

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
    public void T03_Add_Place_Too_Long_Name_Return_Status_CONFLICT(){

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
    public void T04_Add_Place_Too_Short_Discipline_Return_Status_CONFLICT(){

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
    public void T05_Add_Place_Too_Long_Discipline_Return_Status_CONFLICT(){

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
    public void T06_Add_Place_Too_Short_Localization_City_Return_Status_CONFLICT(){

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
    public void T07_Add_Place_Too_Long_Localization_City_Return_Status_CONFLICT(){

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
    public void T08_Add_Place_Too_Short_Localization_Street_Return_Status_CONFLICT(){

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
    public void T09_Add_Place_Too_Long_Localization_Street_Return_Status_CONFLICT(){

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
    public void T10_Add_Place_Too_Short_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toShort = PodiumLimits.minBuildingNumberLength - 1;
        intValueHolder = requestDto.getPlaceLocalization().getBuildingNumber();
        requestDto.getPlaceLocalization().setBuildingNumber(toShort);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setBuildingNumber(intValueHolder);

    }

    @Test
    public void T11_Add_Place_Too_Long_Localization_BuildingNumber_Return_Status_CONFLICT(){

        int toLong = PodiumLimits.maxBuildingNumberLength + 1;
        intValueHolder = requestDto.getPlaceLocalization().getBuildingNumber();
        requestDto.getPlaceLocalization().setBuildingNumber(toLong);

        PlaceValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setBuildingNumber(intValueHolder);

    }









    @Test
    public void T22_Add_Place_Empty_Discipline_Return_Status_CONFLICT(){

        textValueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline("");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.setDiscipline(textValueHolder);
    }

    @Test
    public void T23_Add_Place_Empty_Localization_City_Return_Status_CONFLICT(){

        textValueHolder = requestDto.getPlaceLocalization().getCity();
        requestDto.getPlaceLocalization().setCity("");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getPlaceLocalization().setCity(textValueHolder);
    }

    @Test
    public void T24_Add_Place_Empty_OpeningDay_Day_Return_Status_CONFLICT(){

        textValueHolder = requestDto.getOpeningDays().get(1).getDay();
        requestDto.getOpeningDays().get(1).setDay("");

        PlaceValidator
                .getInstance()
                .add(requestDto, HttpStatus.CONFLICT);

        requestDto.getOpeningDays().get(1).setDay(textValueHolder);
    }






   public void test(){

       for (int i = 0; i < 3; i++) {


           this.doSomething();

       }

   }


    @Test
    public void doSomething(){

    }





}
