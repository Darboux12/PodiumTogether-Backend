package com.podium.controller.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingCategoryAddControllerRequest {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minRatingCategoryLength, max = PodiumLimits.maxRatingCategoryLength)
    private String category;
}
