package com.podium.model.dto.request;

import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
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