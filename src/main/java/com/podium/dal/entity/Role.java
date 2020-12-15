package com.podium.dal.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ROLE")
@NoArgsConstructor
public class Role {

    @Id
    @NotNull
    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
