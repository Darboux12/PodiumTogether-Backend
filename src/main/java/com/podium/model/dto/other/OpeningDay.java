package com.podium.model.dto.other;

import com.podium.model.dto.validation.annotation.dto.PodiumNotNull;
import com.podium.model.dto.validation.annotation.dto.PodiumOptionalValue;
import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
import com.podium.model.dto.validation.annotation.dto.PodiumWeekDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningDay {

    @PodiumTextNotEmpty
    @PodiumWeekDay
    private String day;
    @PodiumNotNull
    private boolean isOpen;
    @PodiumOptionalValue
    private LocalTime openingTimeFrom;
    @PodiumOptionalValue
    private LocalTime openingTimeTo;

}
