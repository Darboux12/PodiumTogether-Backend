package com.podium.controller.dto.response;

import com.podium.controller.dto.other.FileControllerDto;
import com.podium.controller.dto.other.StarRatingControllerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewControllerResponse {
    private int id;
    private Set<StarRatingControllerDto> starRatings;
    private String opinion;
    private String author;
    private String place;
    Set<FileControllerDto> images;
    private int likes;
    private int dislikes;
    private Date date;
}
