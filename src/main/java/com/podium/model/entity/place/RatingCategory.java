package com.podium.model.entity.place;

import com.podium.model.entity.event.Event;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RATING_CATEGORY")
public class RatingCategory {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy="category")
    private Set<StarRating> starRatings = new HashSet<>();







}
