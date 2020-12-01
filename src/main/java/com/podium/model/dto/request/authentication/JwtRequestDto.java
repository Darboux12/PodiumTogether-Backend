package com.podium.model.dto.request.authentication;

import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDto{

    @PodiumTextNotEmpty
    private String username;
    @PodiumTextNotEmpty
    private String password;

}