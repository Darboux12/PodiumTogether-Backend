package com.podium.api;

import com.podium.helper.*;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.entity.City;
import com.podium.validator.CityValidator;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CityTest {

    private static CityRequestDto requestDto;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = Constant.getValidCityRequestDto();
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
    public void T03_Find_All_City_Should_Return_Status_OK_Containing_Added_City_Name(){

        boolean isPresent = CityValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CityResponseDto::getCity)
                .anyMatch(requestDto.getCity()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T04_Find_City_By_Name_Should_Return_Status_OK_And_Added_City(){

        String cityName =

        CityValidator
                .getInstance()
                .findByName(requestDto.getCity(),HttpStatus.OK)
                .getCity();

        Assert.assertEquals(requestDto.getCity(),cityName);


    }

    @Test
    public void T05_Exist_City_By_Name_Should_Return_Status_OK(){

        CityValidator
                .getInstance()
                .existCityByName(requestDto.getCity(),HttpStatus.OK);

    }

    @Test
    public void T06_Delete_City_Should_Return_Status_OK_Deleting_Created_City(){

        CityValidator
                .getInstance()
                .deleteCityByName(requestDto.getCity(),HttpStatus.OK);

    }

    @Test
    public void T07_Exist_Deleted_City_By_Name_Should_Return_Status_BAD_REQUEST(){

        CityValidator
                .getInstance()
                .existCityByName(requestDto.getCity(),HttpStatus.BAD_REQUEST);

    }

    @Test
    public void T08_Delete_Created_City_Again_Should_Return_Status_NOTFOUND(){

        CityValidator
                .getInstance()
                .deleteCityByName(requestDto.getCity(),HttpStatus.NOT_FOUND);

    }

}
