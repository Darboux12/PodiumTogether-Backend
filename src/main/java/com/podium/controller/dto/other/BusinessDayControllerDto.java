package com.podium.controller.dto.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import com.podium.controller.validation.annotation.PodiumWeekDay;
import com.podium.controller.validation.annotation.PodiumNotNull;
import com.podium.controller.validation.annotation.PodiumOptionalValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDayControllerDto {

    @PodiumTextNotEmpty
    @PodiumWeekDay
    private String day;
    @PodiumNotNull
    @JsonProperty
    private boolean isOpen;
    @PodiumOptionalValue
    private LocalTime openingTimeFrom;
    @PodiumOptionalValue
    private LocalTime openingTimeTo;

}
