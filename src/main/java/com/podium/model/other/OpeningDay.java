package com.podium.model.other;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OpeningDay {

    private String day;
    private boolean isOpen;
    private boolean isOpenTimeLimit;
    private String openingHourFrom;
    private String openingHourTo;

}
