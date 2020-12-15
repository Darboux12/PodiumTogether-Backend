package com.podium.controller.dto.response;

import com.podium.dal.entity.Event;
import com.podium.controller.dto.other.PodiumFileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String email;
    private String password;
    private String country;
    private Set<String> roles;
    private Date birthday;
    private PodiumFileDto profileImage;
    private String description;
}
