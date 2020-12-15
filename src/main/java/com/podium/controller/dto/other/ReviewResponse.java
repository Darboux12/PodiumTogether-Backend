package com.podium.controller.dto.other;

import com.podium.dal.entity.Place;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private int id;
    private Set<RatingDto> starRatings;
    private String opinion;
    private String author;
    private String place;
    List<PodiumFileDto> images;
    private int likes;
    private int dislikes;


}
