package com.podium.model.dto.request;

import com.podium.model.other.OpeningDay;
import com.podium.model.other.PlaceLocalization;
import com.podium.model.other.Rating;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PlaceRequestDto {

    private String name;
    private String discipline;
    private PlaceLocalization placeLocalization;
    private List<OpeningDay> openingDays;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private List<Rating> ratings;
    private String review;

}
