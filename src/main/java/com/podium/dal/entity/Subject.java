package com.podium.dal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "SUBJECT")
@NoArgsConstructor
public class Subject {

    @NotNull
    @Id
    @Column(name = "subject")
    private String subject;

    @OneToMany(mappedBy="subject")
    private Set<Contact> contacts = new HashSet<>();

    public Subject(String subject) {
        this.subject = subject;
    }
}
