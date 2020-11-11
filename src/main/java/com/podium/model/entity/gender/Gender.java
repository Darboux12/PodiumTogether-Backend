package com.podium.model.entity.gender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.podium.model.entity.event.Event;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "GENDER")
public class Gender {

    @Id
    @NotNull
    @Column(name = "gender")
    private String gender;

    @ManyToMany(mappedBy = "genders")
    private Set<Event> events = new HashSet<>();

    public Gender(String gender) {
        this.gender = gender;
    }
}
