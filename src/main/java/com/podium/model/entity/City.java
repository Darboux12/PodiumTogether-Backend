package com.podium.model.entity;

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
@Table(name = "CITY")
public class City {

    @Id
    @NotNull
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy="city")
    private Set<Localization> localizations = new HashSet<>();

    public City(String city){
        this.city = city;
    }

}
