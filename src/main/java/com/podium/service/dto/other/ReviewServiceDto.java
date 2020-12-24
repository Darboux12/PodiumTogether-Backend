package com.podium.service.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewServiceDto {
    private int id;
    private Set<StarRatingServiceDto> starRatings;
    private String opinion;
    private String author;
    private String place;
    private Set<FileServiceDto> images;
    private int likes;
    private int dislikes;
}
