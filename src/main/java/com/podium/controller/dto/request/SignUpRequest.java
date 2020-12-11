package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumDatePast;
import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import com.podium.controller.validation.annotation.PodiumValidEmail;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {


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
}
