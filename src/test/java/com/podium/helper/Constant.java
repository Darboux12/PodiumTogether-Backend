package com.podium.helper;

import com.podium.model.dto.request.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constant {

    private static EventRequestDto validEventRequestDto = new EventRequestDto();

    public static EventRequestDto getValidEventRequestDto() throws ParseException {

        validEventRequestDto.setTitle("TestEventTitle");

        Date dateFrom= new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss:ms").parse("2021-02-13 17:00:00:00");
        Date dateTo= new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss:ms").parse("2021-02-20 13:00:00:00");

        validEventRequestDto.setDateFrom(dateFrom);
        validEventRequestDto.setDateTo(dateTo);
        String existingCity = "London";
        validEventRequestDto.setCity(existingCity);
        validEventRequestDto.setNumber(433);
        String existingStreet = "Polna";
        validEventRequestDto.setStreet(existingStreet);
        validEventRequestDto.setPostal("23-203");
        String existingDiscipline = "Football";
        validEventRequestDto.setDiscipline(existingDiscipline);
        validEventRequestDto.setPeople(12);

        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");

        validEventRequestDto.setGenders(genders);
        validEventRequestDto.setMinAge(12);
        validEventRequestDto.setMaxAge(43);
        validEventRequestDto.setCost(12);
        validEventRequestDto.setDescription("This is test description");
        validEventRequestDto.setAuthor("johndoe");

        return validEventRequestDto;


    }

}
