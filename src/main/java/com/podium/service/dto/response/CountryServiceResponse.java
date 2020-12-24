package com.podium.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryServiceResponse {
    private String countryId;
    private String name;
    private String printable_name;
    private String iso3;
    private Integer numCode;
}
