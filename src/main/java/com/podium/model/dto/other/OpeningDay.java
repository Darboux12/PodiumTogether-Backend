package com.podium.model.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.validation.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
