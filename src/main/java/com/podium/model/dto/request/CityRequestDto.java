package com.podium.model.dto.request;

import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.entity.localization.City;
import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CityRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;

    public CityRequestDto(String city){
        this.city = city;

    }
}
