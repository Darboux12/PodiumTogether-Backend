package com.podium.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PODIUM_RESOURCES")
public class PodiumResource {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "resource_id")
    private int resourceId;

    @NotNull
    @Column(name="name")
    private String name;

    @NotNull
    @Column(name="type")
    private String type;

    @NotNull
    @Column(name="path")
    private String path;

    @ManyToMany(mappedBy = "newsResources")
    private Set<News> news = new HashSet<>();

    @ManyToMany(mappedBy = "eventResources")
    private Set<Event> events = new HashSet<>();

    @ManyToMany(mappedBy = "profileImages")
    private Set<User> users = new HashSet<>();


}
