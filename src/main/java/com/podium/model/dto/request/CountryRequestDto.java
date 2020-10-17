package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumNotNull;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryRequestDto {

    @PodiumTextNotEmpty
    private String countryId;
    @PodiumTextNotEmpty
    private String name;
    @PodiumTextNotEmpty
    private String printableName;
    @PodiumTextNotEmpty
    private String iso3;
    private Integer numCode;
}
