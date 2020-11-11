package com.podium.model.entity.localization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.podium.model.entity.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
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
@Table(name = "COUNTRY")
public class Country {

    @Id
    @Column(name = "country_id", columnDefinition = "char")
    private String countryId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "printable_name")
    private String printableName;

    @Column(name = "iso3", columnDefinition = "char")
    private String iso3;

    @Column(name = "numcode",columnDefinition = "smallint")
    private Integer numCode;

    @OneToMany(mappedBy="country")
    private Set<User> users = new HashSet<>();

    public Country(String countryId, String name, String printableName, String iso3, Integer numCode) {
        this.countryId = countryId;
        this.name = name;
        this.printableName = printableName;
        this.iso3 = iso3;
        this.numCode = numCode;
    }
}
