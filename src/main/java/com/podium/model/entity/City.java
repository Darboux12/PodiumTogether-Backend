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
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","localizations"})
@Table(name = "CITY")
public class City {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "city_id")
    private int cityId;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="city")
    private Set<Localization> localizations = new HashSet<>();
}
