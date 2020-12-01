package com.podium.model.dto.request.contact;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minSubjectLength, max = PodiumLimits.maxSubjectLength)
    private String subject;

}
