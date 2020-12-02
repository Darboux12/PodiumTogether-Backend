package com.podium.model.dto.request.place;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.validation.annotation.PodiumLength;
import com.podium.model.dto.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingCategoryRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minRatingCategoryLength, max = PodiumLimits.maxRatingCategoryLength)
    private String category;
}
