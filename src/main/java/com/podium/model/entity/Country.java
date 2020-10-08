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
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","users"})
@Table(name = "COUNTRY")
public class Country {


    @Id
    @Column(name = "country_id")
    private String countryId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "printable_name")
    private String printable_name;

    @NotNull
    @Column(name = "iso3")
    private String iso3;

    @Column(name = "numcode")
    private short numCode;

    @OneToMany(mappedBy="country")
    private Set<User> users = new HashSet<>();
}
