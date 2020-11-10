package com.podium.model.entity.localization;


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

    @Id
    @NotNull
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy="city")
    private Set<Localization> localizations = new HashSet<>();
}
