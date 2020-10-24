package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minSubjectLength, max = PodiumLimits.maxSubjectLength)
    private String subject;

}
