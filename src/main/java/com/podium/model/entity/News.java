package com.podium.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "NEWS")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","newsResources"})
public class News {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "news_id")
    private int newsId;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "shortText")
    @Type(type = "text")
    private String shortText;

    @NotNull
    @Column(name = "text")
    @Type(type = "text")
    private String text;

    @NotNull
    @Column(name = "link_text")
    private String linkText;

    @NotNull
    @Column(name = "date")
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "news_resource",
            joinColumns = { @JoinColumn(name = "news_id") },
            inverseJoinColumns = { @JoinColumn(name = "resource_id") })
    private Set<PodiumResource> newsResources = new HashSet<>();




}
