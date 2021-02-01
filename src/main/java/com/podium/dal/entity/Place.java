package com.podium.dal.entity;

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
@Table(name = "PLACE")
@NoArgsConstructor
public class Place {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "place_id")
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="discipline", nullable=false)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="localization_id", nullable=false)
    private Localization placeLocalization;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "place_business",
            joinColumns = { @JoinColumn(name = "place_id") },
            inverseJoinColumns = { @JoinColumn(name = "business_day_id") })
    private Set<BusinessDay> businessDays = new HashSet<>();

    @NotNull
    @Column(name = "cost")
    private double cost;

    @NotNull
    @Column(name = "usage_time")
    private double usageTime;

    @NotNull
    @Column(name = "min_age")
    private int minAge;

    @NotNull
    @Column(name = "max_age")
    private int maxAge;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="user_id", nullable=false)
    private User author;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "place_resource",
            joinColumns = { @JoinColumn(name = "place_id") },
            inverseJoinColumns = { @JoinColumn(name = "resource_id") })
    private Set<PodiumResource> placeResources = new HashSet<>();

    @OneToMany(mappedBy="place")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy="place")
    private Set<Event> events = new HashSet<>();

    public Place(String name, Discipline discipline, Localization placeLocalization, Set<BusinessDay> businessDays, double cost, double usageTime, int minAge, int maxAge,User author, Set<PodiumResource> placeResources) {
        this.name = name;
        this.discipline = discipline;
        this.placeLocalization = placeLocalization;
        this.businessDays = businessDays;
        this.cost = cost;
        this.usageTime = usageTime;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.placeResources = placeResources;
        this.author = author;
    }

    public Place(String name, Discipline discipline, Localization placeLocalization, Set<BusinessDay> businessDays, double cost, double usageTime, int minAge, int maxAge, User author) {
        this.name = name;
        this.discipline = discipline;
        this.placeLocalization = placeLocalization;
        this.businessDays = businessDays;
        this.cost = cost;
        this.usageTime = usageTime;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.author = author;
    }
}
