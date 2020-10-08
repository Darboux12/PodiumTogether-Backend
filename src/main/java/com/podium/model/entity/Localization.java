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
    @JoinColumn(name="city_id", nullable=false)
    private City city;

    @NotNull
    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "postal_code")
    private String postalCode;

    @OneToMany(mappedBy="localization")
    private Set<Event> events = new HashSet<>();

}
