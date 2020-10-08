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

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "gender_id")
    private int genderId;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="gender")
    private Set<Event> events = new HashSet<>();
}
