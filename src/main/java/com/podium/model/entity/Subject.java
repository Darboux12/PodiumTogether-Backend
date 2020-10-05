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

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "subject_id")
    private int subjectId;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="subject")
    private Set<Contact> contacts = new HashSet<>();

}
