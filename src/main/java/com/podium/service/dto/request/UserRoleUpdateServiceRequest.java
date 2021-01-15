package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRoleUpdateServiceRequest {
    private String username;
    private String role;
}
