package com.podium.model.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.model.dto.validation.annotation.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDto {

    @PodiumNotNull
    private int id;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minUsernameLength, max = PodiumLimits.maxUsernameLength)
    private String username;
    @PodiumTextNotEmpty
    @PodiumValidEmail
    @PodiumLength(max = PodiumLimits.maxEmailLength)
    private String email;
    @PodiumLength(min = PodiumLimits.minPasswordLength, max=PodiumLimits.maxPasswordLength)
    private String password;
    @PodiumTextNotEmpty
    private String country;
    @PodiumDatePast
    private Date birthday;
    @PodiumLength(max = PodiumLimits.maxUserDescriptionLength)
    private String description;
}
