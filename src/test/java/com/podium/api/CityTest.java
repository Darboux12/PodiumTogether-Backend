package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.entity.localization.City;
import com.podium.validator.CityValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CityTest {

    private static CityRequestDto requestDto;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = new CityRequestDto("TestCityName");
    }

    @Test
    public void T01_Add_Valid_City_Should_Return_Status_OK(){

        CityValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);
    }

    @Test
    public void T02_Add_Same_City_Again_Should_Return_Status_CONFLICT(){
        CityValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);
    }

    @Test
    public void T03_Add_Empty_City_Should_Return_Status_CONFLICT(){

        CityValidator
                .getInstance()
                .add(new CityRequestDto(""),HttpStatus.CONFLICT);
    }

    @Test
    public void T04_Add_To_Long_City_Should_Return_Status_CONFLICT(){

        String toLongCityName =
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);

        CityValidator
                .getInstance()
                .add(new CityRequestDto(toLongCityName),HttpStatus.CONFLICT);
    }

    @Test
    public void T05_Add_To_Short_City_Should_Return_Status_CONFLICT(){

        String toShortCityName =
                StringUtils.repeat("*", PodiumLimits.minCityLength - 1);

        CityValidator
                .getInstance()
                .add(new CityRequestDto(toShortCityName),HttpStatus.CONFLICT);
    }

    @Test
    public void T06_Find_All_City_Should_Return_Status_OK_Containing_Added_City_Name(){

        boolean isPresent = CityValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CityResponseDto::getCity)
                .anyMatch(requestDto.getCity()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T07_Find_All_City_Should_Return_Status_OK_Containing_Katowice_Cairo(){

        List<String> responseCities = CityValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CityResponseDto::getCity)
                .collect(Collectors.toList());

        Assert.assertTrue
                (responseCities.containsAll(List.of("Cairo","Katowice")));

    }

    @Test
    public void T09_Find_City_By_Name_Should_Return_Status_OK_And_Added_City(){

        String cityName =

        CityValidator
                .getInstance()
                .findByName(requestDto.getCity(),HttpStatus.OK)
                .getCity();

        Assert.assertEquals(requestDto.getCity(),cityName);

    }

    @Test
    public void T10_Find_City_By_Name_Not_Exist_Should_Return_Status_NOT_FOUND(){

        CityValidator
                .getInstance()
                .findByName("NOT EXISTING CITY NAME",HttpStatus.NOT_FOUND);

    }

    @Test
    public void T11_Exist_City_By_Name_Should_Return_Status_OK(){

        CityValidator
                .getInstance()
                .existCityByName(requestDto.getCity(),HttpStatus.OK);

    }

    @Test
    public void T12_Delete_City_Should_Return_Status_OK_Deleting_Created_City(){

        CityValidator
                .getInstance()
                .deleteCityByName(requestDto.getCity(),HttpStatus.OK);

    }

    @Test
    public void T13_Exist_Deleted_City_By_Name_Should_Return_Status_BAD_REQUEST(){

        CityValidator
                .getInstance()
                .existCityByName(requestDto.getCity(),HttpStatus.BAD_REQUEST);

    }

    @Test
    public void T14_Delete_Created_City_Again_Should_Return_Status_NOTFOUND(){

        CityValidator
                .getInstance()
                .deleteCityByName(requestDto.getCity(),HttpStatus.NOT_FOUND);
    }

}
