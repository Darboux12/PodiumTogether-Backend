package com.podium.model.dto.request;

import com.podium.validation.annotation.*;
import com.podium.constant.PodiumLimits;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minEventTitleLength, max = PodiumLimits.maxEventTitleLength)
    private String title;
    @PodiumDateFuture
    private Date dateFrom;
    @PodiumDateFuture
    private Date dateTo;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minCityLength, max = PodiumLimits.maxCityLength)
    private String city;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minBuildingNumberLength, max = PodiumLimits.maxBuildingNumberLength)
    private int number;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minStreetLength, max = PodiumLimits.maxStreetLength)
    private String street;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minPostalLength, max = PodiumLimits.maxPostalLength)
    private String postal;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minDisciplineLength, max = PodiumLimits.maxDisciplineLength)
    private String discipline;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minEventPeopleLength, max = PodiumLimits.maxEventPeopleLength)
    private int people;
    @PodiumNotNull
    private List<String> genders;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minEventMinAge, max = PodiumLimits.maxEventMinAge)
    private int minAge;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minEventMaxAge, max = PodiumLimits.maxEventMaxAge)
    private int maxAge;
    @PodiumNotNull
    @PodiumNumberDouble(min = PodiumLimits.minCost, max = PodiumLimits.maxCost)
    private double cost;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minEventDescriptionLength, max = PodiumLimits.maxEventDescriptionLength)
    private String description;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minUsernameLength, max = PodiumLimits.maxUsernameLength)
    private String author;

}
