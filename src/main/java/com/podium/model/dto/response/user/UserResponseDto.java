package com.podium.model.dto.response.user;

import com.podium.model.entity.event.Event;
import com.podium.model.dto.other.PodiumFile;
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
    private PodiumFile profileImage;
    private Set<Event> eventsJoined;
    private Set<Event> eventsCreated;
    private String description;
}
