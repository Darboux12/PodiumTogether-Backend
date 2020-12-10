package com.podium.model.dto.response;

import com.podium.model.entity.Event;
import com.podium.model.dto.other.PodiumFileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private String country;
    private Set<String> roles;
    private Date birthday;
    private PodiumFileDto profileImage;
    private Set<Event> eventsJoined;
    private Set<Event> eventsCreated;
    private String description;
}
