package com.podium.controller.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EventAddControllerRequest {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minEventTitleLength, max = PodiumLimits.maxEventTitleLength)
    private String title;
    @PodiumDateFuture
    private Date dateFrom;
    @PodiumDateFuture
    private Date dateTo;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minEventPeopleLength, max = PodiumLimits.maxEventPeopleLength)
    private int people;
    @PodiumNotNull
    private Set<String> genders;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minEventMinAge, max = PodiumLimits.maxEventMinAge)
    private int minAge;
    @PodiumNotNull
    @PodiumNumberInt(min = PodiumLimits.minEventMaxAge, max = PodiumLimits.maxEventMaxAge)
    private int maxAge;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minEventDescriptionLength, max = PodiumLimits.maxEventDescriptionLength)
    private String description;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minUsernameLength, max = PodiumLimits.maxUsernameLength)
    private String author;
    @PodiumTextNotEmpty
    private String placeName;
}
