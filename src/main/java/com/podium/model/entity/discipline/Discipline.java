package com.podium.model.entity.discipline;

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
@Table(name = "DISCIPLINE")
public class Discipline {

    @Id
    @NotNull
    @Column(name = "discipline")
    private String discipline;

    @OneToMany(mappedBy="discipline")
    private Set<Event> events = new HashSet<>();

    public Discipline(String discipline) {
        this.discipline = discipline;
    }

}
