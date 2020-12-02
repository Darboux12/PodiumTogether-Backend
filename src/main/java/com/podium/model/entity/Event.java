package com.podium.model.entity;

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
@Table(name = "EVENT")
@SecondaryTable(name = "EVENT_DETAILS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "event_details_id"))
public class Event {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "event_id")
    private int eventId;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "date_from", table = "EVENT_DETAILS")
    private Date dateFrom;

    @NotNull
    @Column(name = "date_to", table = "EVENT_DETAILS")
    private Date dateTo;

    @NotNull
    @Column(name = "people_number", table = "EVENT_DETAILS")
    private int peopleNumber;

    @NotNull
    @Column(name = "min_age", table = "EVENT_DETAILS")
    private int minAge;

    @NotNull
    @Column(name = "max_age", table = "EVENT_DETAILS")
    private int maxAge;

    @NotNull
    @Column(name = "cost", table = "EVENT_DETAILS")
    private double cost;

    @NotNull
    @Type(type = "text")
    @Column(name = "description", table = "EVENT_DETAILS")
    private String description;

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
    @JoinColumn(name="localization_id", nullable=false)
    private Localization localization;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="discipline", nullable=false)
    private Discipline discipline;

    @Column(name = "views")
    private int views;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "event_resource",
            joinColumns = { @JoinColumn(name = "event_id") },
            inverseJoinColumns = { @JoinColumn(name = "resource_id") })
    private Set<PodiumResource> eventResources = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "event_gender",
            joinColumns = { @JoinColumn(name = "event_id") },
            inverseJoinColumns = { @JoinColumn(name = "gender") })
    private Set<Gender> genders = new HashSet<>();

    @Column(name = "creation_date")
    private Date creationDate;

}
