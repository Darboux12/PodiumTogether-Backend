package com.podium.model.dto.request;

import com.podium.model.dto.validation.annotation.dto.PodiumLength;
import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
import com.podium.model.dto.validation.annotation.dto.PodiumValidEmail;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
