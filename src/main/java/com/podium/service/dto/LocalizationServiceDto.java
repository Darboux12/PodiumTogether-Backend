package com.podium.service.dto;

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
public class LocalizationServiceDto {
    private String city;
    private String street;
    private int buildingNumber;
    private String postalCode;
    private String localizationRemarks;
}
