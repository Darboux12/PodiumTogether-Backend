package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.validation.validators.PodiumLimits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenderRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minGenderLength, max = PodiumLimits.maxGenderLength)
    private String gender;
}
