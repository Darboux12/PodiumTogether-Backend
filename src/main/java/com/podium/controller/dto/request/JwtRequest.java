package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    @PodiumTextNotEmpty
    private String username;
    @PodiumTextNotEmpty
    private String password;

}