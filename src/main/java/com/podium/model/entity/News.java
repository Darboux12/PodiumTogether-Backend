package com.podium.model.entity;


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

    @OneToMany(mappedBy="news")
    private Set<PodiumResource> newsResources = new HashSet<>();

}
