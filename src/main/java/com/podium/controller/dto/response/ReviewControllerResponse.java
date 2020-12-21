package com.podium.controller.dto.response;

import com.podium.controller.dto.other.FileControllerDto;
import com.podium.controller.dto.other.RatingControllerDto;
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
public class ReviewControllerResponse {
    private int id;
    private Set<RatingControllerDto> starRatings;
    private String opinion;
    private String author;
    private String place;
    List<FileControllerDto> images;
    private int likes;
    private int dislikes;


}
