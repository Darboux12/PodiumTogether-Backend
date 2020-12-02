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
@Table(name = "STAR_RATING")
public class StarRating {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "star_rating_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="category", nullable=false)
    private RatingCategory category;

    @NotNull
    @Column(name = "rating")
    private int rating;

    @ManyToMany(mappedBy = "starRatings")
    private Set<Review> places = new HashSet<>();

}
