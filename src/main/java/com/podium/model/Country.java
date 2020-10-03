package com.podium.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "COUNTRY")
public class Country {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "country_id")
    private int countryId;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<User> users;
}
