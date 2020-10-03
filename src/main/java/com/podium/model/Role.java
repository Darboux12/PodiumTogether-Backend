package com.podium.model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "role_id")
    private int roleId;

    @NotNull
    @Column(name = "name")
    private String name;

}
