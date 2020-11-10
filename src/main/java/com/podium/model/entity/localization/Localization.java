package com.podium.model.entity.localization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.podium.model.entity.event.Event;
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
@Table(name = "LOCALIZATION")
public class Localization {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "localization_id")
    private int localizationId;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="city", nullable=false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="street", nullable=false)
    private Street street;


    @NotNull
    @Column(name = "building_number")
    private int buildingNumber;

    @NotNull
    @Column(name = "postal_code")
    private String postalCode;

    @NotNull
    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy="localization")
    private Set<Event> events = new HashSet<>();

}
