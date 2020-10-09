package com.podium.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","eventFiles"})
@Table(name = "EVENT")
public class Event {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "event_id")
    private int eventId;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "people_number")
    private int people_number;

    @NotNull
    @Column(name = "min_age")
    private int min_age;

    @NotNull
    @Column(name = "max_age")
    private int max_age;

    @NotNull
    @Column(name = "cost")
    private double cost;

    @NotNull
    @Column(name = "duration")
    private double duration;

    @NotNull
    @Type(type = "text")
    @Column(name = "description")
    private double description;

    @OneToMany(mappedBy="event")
    private Set<EventFile> eventFiles = new HashSet<>();

    @ManyToMany(mappedBy = "eventsJoined")
    private Set<User> usersJoined = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="user_id", nullable=false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="gender_id", nullable=false)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="localization_id", nullable=false)
    private Localization localization;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="discipline_id", nullable=false)
    private Discipline discipline;

    @Column(name = "views")
    private int views;

}
