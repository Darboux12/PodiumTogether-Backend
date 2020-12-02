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
@Table(name = "STREET")
public class Street {

    @Id
    @NotNull
    @Column(name = "street")
    private String street;

    @OneToMany(mappedBy="street")
    private Set<Localization> localizations = new HashSet<>();

    public Street(String street) {
        this.street = street;
    }
}
