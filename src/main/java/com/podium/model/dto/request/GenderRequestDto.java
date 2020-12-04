package com.podium.model.dto.request;

import com.podium.model.dto.validation.annotation.dto.PodiumLength;
import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenderRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minGenderLength, max = PodiumLimits.maxGenderLength)
    private String gender;
}
