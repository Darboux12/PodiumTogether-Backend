package com.podium.model.dto.other;

import com.podium.validation.annotation.PodiumCollectionTextNotEmpty;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.validation.annotation.PodiumTime;
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
    private String day;
    private boolean isOpen;
    private boolean isOpenTimeLimit;
    @PodiumTime
    private String openingTimeFrom;
    private String openingTimeTo;

}
