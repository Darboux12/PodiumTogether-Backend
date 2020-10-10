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
@Table(name = "DISCIPLINE")
public class Discipline {

    @Id
    @NotNull
    @Column(name = "discipline")
    private String discipline;

    @OneToMany(mappedBy="discipline")
    private Set<Event> events = new HashSet<>();




}
