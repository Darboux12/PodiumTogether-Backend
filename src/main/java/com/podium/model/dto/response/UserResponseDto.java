package com.podium.model.dto.response;

import com.podium.model.entity.event.Event;
import com.podium.model.other.PodiumFile;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private String country;
    private Set<String> roles;
    private Date birthday;
    private PodiumFile profileImage = new PodiumFile();
    private Set<Event> eventsJoined;
    private Set<Event> eventsCreated;
    private String description;
}
