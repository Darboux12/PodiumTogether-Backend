package com.podium.dal.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "REVIEW")
public class Review {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "review_id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "review_rating",
            joinColumns = { @JoinColumn(name = "review_id") },
            inverseJoinColumns = { @JoinColumn(name = "star_rating_id") })
    private Set<StarRating> starRatings = new HashSet<>();

    @NotNull
    @Column(name = "opinion")
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="author", nullable=false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="place_id", nullable=false)
    private Place place;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "review_resource",
            joinColumns = { @JoinColumn(name = "review_id") },
            inverseJoinColumns = { @JoinColumn(name = "resource_id") })
    private Set<PodiumResource> images = new HashSet<>();

    @NotNull
    @Column(name = "likes")
    private int likes;

    @NotNull
    @Column(name = "dislikes")
    private int dislikes;

    public Review(Set<StarRating> starRatings, String opinion, User author, Place place, Set<PodiumResource> images, int likes, int dislikes) {
        this.starRatings = starRatings;
        this.opinion = opinion;
        this.author = author;
        this.place = place;
        this.images = images;
        this.likes = likes;
        this.dislikes = dislikes;
    }
}
