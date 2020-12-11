package com.podium.dal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RATING_CATEGORY")
@NoArgsConstructor
public class RatingCategory {

    @Id
    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy="category")
    private Set<StarRating> starRatings = new HashSet<>();

    public RatingCategory(String category) {
        this.category = category;
    }
}
