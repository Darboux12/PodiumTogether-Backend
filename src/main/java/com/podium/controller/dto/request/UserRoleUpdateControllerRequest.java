package com.podium.controller.dto.request;

import com.podium.constant.PodiumLimits;
import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleUpdateControllerRequest {
    @PodiumTextNotEmpty
    private String username;
    @PodiumTextNotEmpty
    private String role;
}
