package com.podium.api;

import com.podium.model.dto.response.WeekDayResponseDto;
import com.podium.validator.WeekDayValidator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeekDayTest {

    @Test
    public void Find_All_Week_Day_Containing_All_Week_Days(){

        List<String> weekDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        );

        List<String> responseDtos =
                WeekDayValidator
                        .getInstance()
                        .findAll().stream().map(WeekDayResponseDto::getDay)
                        .collect(Collectors.toList());

        Assert.assertTrue(weekDays.containsAll(responseDtos));

    }
}
