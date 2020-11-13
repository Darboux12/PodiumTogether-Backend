package com.podium.model.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumNotNull;
import com.podium.validation.annotation.PodiumNumberInt;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceLocalization {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;
    @PodiumLength(min = PodiumLimits.minStreetLength, max = PodiumLimits.maxStreetLength)
    private String street;
    @PodiumNumberInt(min = PodiumLimits.minBuildingNumberLength, max = PodiumLimits.maxBuildingNumberLength)
    private int buildingNumber;
    @PodiumLength(min = PodiumLimits.minPostalLength, max = PodiumLimits.maxPostalLength)
    private String postalCode;
    @PodiumLength(min = PodiumLimits.minLocalizationRemarksLength, max = PodiumLimits.maxLocalizationRemarksLength)
    private String localizationRemarks;

}
