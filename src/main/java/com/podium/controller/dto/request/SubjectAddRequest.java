package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAddRequest {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minSubjectLength, max = PodiumLimits.maxSubjectLength)
    private String subject;

}
