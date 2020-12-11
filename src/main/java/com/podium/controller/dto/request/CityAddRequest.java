package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CityAddRequest {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;

    public CityAddRequest(String city){
        this.city = city;

    }
}
