package com.podium.model.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.validation.annotation.dto.PodiumLength;
import com.podium.model.dto.validation.annotation.dto.PodiumNumberInt;
import com.podium.model.dto.validation.annotation.dto.PodiumOptionalValue;
import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceLocalization {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;
    @PodiumOptionalValue
    @PodiumLength(min = PodiumLimits.minStreetLength, max = PodiumLimits.maxStreetLength)
    private String street;
    @PodiumOptionalValue
    @PodiumNumberInt(min = PodiumLimits.minBuildingNumberLength, max = PodiumLimits.maxBuildingNumberLength)
    private int buildingNumber;
    @PodiumOptionalValue
    @PodiumLength(min = PodiumLimits.minPostalLength, max = PodiumLimits.maxPostalLength)
    private String postalCode;
    @PodiumOptionalValue
    @PodiumLength(min = PodiumLimits.minLocalizationRemarksLength, max = PodiumLimits.maxLocalizationRemarksLength)
    private String localizationRemarks;

}
