package com.podium.model.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.other.LocalizationDto;
import com.podium.model.dto.other.BusinessDayDto;
import com.podium.model.dto.other.RatingDto;
import com.podium.model.dto.validation.annotation.dto.*;
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
    private LocalizationDto localizationDto;
    @PodiumCollectionLength(min = 7, max = 7)
    private List<BusinessDayDto> businessDayDtos;
    @PodiumNumberDouble(min = PodiumLimits.minCost, max = PodiumLimits.maxCost)
    private double cost;
    @PodiumNumberDouble(min = PodiumLimits.minUsageTimeHours, max = PodiumLimits.maxUsageTimeHours)
    private double usageTime;
    @PodiumNumberInt(min = PodiumLimits.minPlaceMinAge, max = PodiumLimits.maxPlaceMinAge)
    private int minAge;
    @PodiumNumberInt(min = PodiumLimits.minPlaceMaxAge, max = PodiumLimits.maxPlaceMaxAge)
    private int maxAge;
    private List<RatingDto> ratingDtos;
    @PodiumLength(min = PodiumLimits.minPlaceReview, max = PodiumLimits.maxPlaceReview)
    private String review;

}
