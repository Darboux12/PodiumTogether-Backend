package com.podium.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","events"})
@Table(name = "GENDER")
public class Gender {

    @Id
    @NotNull
    @Column(name = "gender")
    private String gender;

    @ManyToMany(mappedBy = "genders")
    private Set<Event> events = new HashSet<>();
}
