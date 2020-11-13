package com.podium.model.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.other.OpeningDay;
import com.podium.model.dto.other.PlaceLocalization;
import com.podium.model.dto.other.Rating;
import com.podium.validation.annotation.PodiumCollectionTextNotEmpty;
import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @PodiumCollectionTextNotEmpty
    private List<OpeningDay> openingDays;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private List<Rating> ratings;
    private String review;

}
