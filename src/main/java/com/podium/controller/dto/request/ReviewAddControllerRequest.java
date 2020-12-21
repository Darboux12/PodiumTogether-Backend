package com.podium.controller.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.other.RatingControllerDto;
import com.podium.controller.validation.annotation.PodiumCollectionTextNotEmpty;
import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAddControllerRequest {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minUsernameLength, max = PodiumLimits.maxUsernameLength)
    private String author;
    @PodiumTextNotEmpty(message = "Place name cannot be empty")
    @PodiumLength(min = PodiumLimits.minPlaceNameLength, max = PodiumLimits.maxPlaceNameLength)
    private String place;
    @PodiumCollectionTextNotEmpty
    private Set<RatingControllerDto> starRatings;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minPlaceReviewOpinion, max = PodiumLimits.maxPlaceReviewOpinion)
    private String opinion;

}
