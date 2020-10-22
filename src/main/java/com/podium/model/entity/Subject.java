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
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","contacts"})
@Table(name = "SUBJECT")
public class Subject {

    @NotNull
    @Id
    @Column(name = "subject")
    private String subject;

    @OneToMany(mappedBy="subject")
    private Set<Contact> contacts = new HashSet<>();

}
