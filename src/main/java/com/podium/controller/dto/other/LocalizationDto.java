package com.podium.controller.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.PodiumNumberInt;
import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumOptionalValue;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalizationDto {

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
