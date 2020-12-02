package com.podium.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @OneToOne(mappedBy = "profileImage")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="review_id", nullable=false)
    private Review review;

}
