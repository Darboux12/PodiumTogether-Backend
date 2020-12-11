package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumNumberInt;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryAddRequest {

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
