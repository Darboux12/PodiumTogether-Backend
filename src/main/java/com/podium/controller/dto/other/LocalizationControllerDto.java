package com.podium.controller.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalizationControllerDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minStreetLength, max = PodiumLimits.maxStreetLength)
    private String street;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minBuildingNumberLength, max = PodiumLimits.maxBuildingNumberLength)
    private int buildingNumber;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minPostalLength, max = PodiumLimits.maxPostalLength)
    private String postalCode;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minLocalizationRemarksLength, max = PodiumLimits.maxLocalizationRemarksLength)
    @PodiumOptionalValue
    private String localizationRemarks;

}
