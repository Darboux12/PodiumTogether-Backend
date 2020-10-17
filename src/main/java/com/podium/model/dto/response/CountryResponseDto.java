package com.podium.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryResponseDto {
    private String countryId;
    private String name;
    private String printable_name;
    private String iso3;
    private Integer numCode;
}
