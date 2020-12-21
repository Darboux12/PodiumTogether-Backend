package com.podium.controller.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.PodiumNumberInt;
import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingControllerDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minRatingCategoryLength, max = PodiumLimits.maxRatingCategoryLength)
    private String category;
    @PodiumNumberInt(min = PodiumLimits.minPlaceRating, max = PodiumLimits.maxPlaceRating)
    private int rating;

}
