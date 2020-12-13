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
    private int placeId;

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
    private Localization localization;

    @ManyToMany
    @JoinTable(
            name = "place_business_day",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "business_day_id"))
    private Set<BusinessDay> businessDays = new HashSet<>();

    @NotNull
    @Column(name = "cost")
    private double cost;

    @NotNull
    @Column(name = "usage_time")
    private double usageTime;

    @NotNull
    @Column(name = "min_age")
    private double minAge;

    @NotNull
    @Column(name = "max_age")
    private double maxAge;

    @OneToMany(mappedBy="place")
    private Set<Review> reviews = new HashSet<>();


    public Place(String name, Discipline discipline, Localization localization, Set<BusinessDay> businessDays, double cost, double usageTime, double minAge, double maxAge) {
        this.name = name;
        this.discipline = discipline;
        this.localization = localization;
        this.businessDays = businessDays;
        this.cost = cost;
        this.usageTime = usageTime;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
}
