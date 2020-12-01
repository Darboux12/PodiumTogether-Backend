package com.podium.model.dto.request.place;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.other.OpeningDay;
import com.podium.model.dto.other.PlaceLocalization;
import com.podium.model.dto.other.Rating;
import com.podium.validation.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequestDto {

    @PodiumTextNotEmpty(message = "Place name cannot be empty")
    @PodiumLength(min = PodiumLimits.minPlaceNameLength, max = PodiumLimits.maxPlaceNameLength)
    private String name;
    @PodiumTextNotEmpty(message = "Place discipline cannot be empty")
    @PodiumLength(min = PodiumLimits.minDisciplineLength, max = PodiumLimits.maxDisciplineLength)
    private String discipline;
    @PodiumCollectionTextNotEmpty
    private PlaceLocalization placeLocalization;
    @PodiumCollectionLength(min = 7, max = 7)
    private List<OpeningDay> openingDays;
    @PodiumNumberDouble(min = PodiumLimits.minCost, max = PodiumLimits.maxCost)
    private double cost;
    @PodiumNumberDouble(min = PodiumLimits.minUsageTimeHours, max = PodiumLimits.maxUsageTimeHours)
    private double usageTime;
    @PodiumNumberInt(min = PodiumLimits.minPlaceMinAge, max = PodiumLimits.maxPlaceMinAge)
    private int minAge;
    @PodiumNumberInt(min = PodiumLimits.minPlaceMaxAge, max = PodiumLimits.maxPlaceMaxAge)
    private int maxAge;
    private List<Rating> ratings;
    @PodiumLength(min = PodiumLimits.minPlaceReview, max = PodiumLimits.maxPlaceReview)
    private String review;

}
