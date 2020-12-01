package com.podium.model.dto.request.localization;

import com.podium.validation.annotation.PodiumNotNull;
import com.podium.validation.annotation.PodiumNumberInt;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequestDto {

    @PodiumTextNotEmpty
    private String countryId;
    @PodiumTextNotEmpty
    private String name;
    @PodiumTextNotEmpty
    private String printableName;
    @PodiumTextNotEmpty
    private String iso3;
    @PodiumNumberInt
    private int numCode;
}
