package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CountryAddServiceDto {
    private String countryId;
    private String name;
    private String printableName;
    private String iso3;
    private int numCode;
}
