package com.podium.service.dto;

import com.podium.controller.dto.other.RatingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAddServiceDto {
    private String author;
    private String place;
    private Set<StarRatingServiceDto> starRatings;
    private String opinion;
    private Set<MultipartFile> images;
}
