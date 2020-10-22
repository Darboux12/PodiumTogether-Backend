package com.podium.model.dto.response;

import com.podium.model.entity.Country;
import com.podium.model.entity.Event;
import com.podium.model.entity.Role;
import com.podium.model.other.PodiumFile;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    private List<PodiumFile> profileImages = new ArrayList<>();
    private Set<Event> eventsJoined;
    private Set<Event> eventsCreated;
}
