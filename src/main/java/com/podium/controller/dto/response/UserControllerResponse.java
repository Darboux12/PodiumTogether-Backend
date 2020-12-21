package com.podium.controller.dto.response;

import com.podium.controller.dto.other.FileControllerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserControllerResponse {
    private int id;
    private String username;
    private String email;
    private String password;
    private String country;
    private Set<String> roles;
    private Date birthday;
    private FileControllerDto profileImage;
    private String description;
}
