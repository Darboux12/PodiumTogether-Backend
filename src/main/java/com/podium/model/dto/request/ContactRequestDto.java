package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.validation.annotation.PodiumValidEmail;
import com.podium.validation.validators.PodiumLimits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDto {

    @PodiumValidEmail
    @PodiumTextNotEmpty
    @PodiumLength(max = PodiumLimits.maxEmailLength)
    private String userEmail;
    @PodiumTextNotEmpty
    private String subject;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minContactMessageLength, max = PodiumLimits.maxContactMessageLength)
    private String message;


}
