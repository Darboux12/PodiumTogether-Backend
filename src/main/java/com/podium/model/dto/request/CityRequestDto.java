package com.podium.model.dto.request;

import com.podium.model.dto.RequestDto;
import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.validation.validators.PodiumLimits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequestDto implements RequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;
}
