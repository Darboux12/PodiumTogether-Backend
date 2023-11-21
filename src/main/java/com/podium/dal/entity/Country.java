package com.podium.dal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
    private int countryId;

    @Column(name = "iso", columnDefinition = "char")
    private String iso;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "nicename")
    private String niceName;

    @Column(name = "iso3", columnDefinition = "char")
    private String iso3;

    @Column(name = "numcode",columnDefinition = "smallint")
    private Integer numCode;

    @Column(name = "phonecode",columnDefinition = "smallint")
    private Integer phoneCode;

    @OneToMany(mappedBy="country")
    private Set<User> users = new HashSet<>();

    public Country(int countryId, String name, String iso3, Integer numCode) {
        this.countryId = countryId;
        this.name = name;
        this.iso3 = iso3;
        this.numCode = numCode;
    }
}
