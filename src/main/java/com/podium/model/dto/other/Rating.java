package com.podium.model.dto.other;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.validation.annotation.PodiumLength;
import com.podium.model.dto.validation.annotation.PodiumNumberInt;
import com.podium.model.dto.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minRatingCategoryLength, max = PodiumLimits.maxRatingCategoryLength)
    private String category;
    @PodiumNumberInt(min = PodiumLimits.minPlaceRating, max = PodiumLimits.maxPlaceRating)
    private int rating;

}
