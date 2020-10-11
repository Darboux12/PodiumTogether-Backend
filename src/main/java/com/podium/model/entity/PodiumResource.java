package com.podium.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "NEWS_RESOURCES")
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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="news_id", nullable=false)
    private News news;


}
