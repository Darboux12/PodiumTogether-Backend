package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import com.podium.controller.validation.annotation.PodiumValidEmail;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactAddControllerRequest {

    @PodiumValidEmail
    @PodiumTextNotEmpty
    @PodiumLength(max = PodiumLimits.maxEmailLength)
    private String userEmail;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minSubjectLength, max = PodiumLimits.maxStreetLength)
    private String subject;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minContactMessageLength, max = PodiumLimits.maxContactMessageLength)
    private String message;

}
