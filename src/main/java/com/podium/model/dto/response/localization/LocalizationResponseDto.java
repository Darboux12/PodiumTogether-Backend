package com.podium.model.dto.response.localization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationResponseDto {

    private int localizationId;
    private String city;
    private String street;
    private int buildingNumber;
    private String postalCode;
    private String remarks;
}
