package com.podium.service.dto.other;

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
@AllArgsConstructor
public class BusinessDayServiceDto {
    private String day;
    private boolean isOpen;
    private LocalTime openingTimeFrom;
    private LocalTime openingTimeTo;
}
