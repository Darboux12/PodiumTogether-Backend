package com.podium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ReviewServiceDto {
    private String opinion;
    Set<StarRatingServiceDto> starRatingServiceDtos;
    private String authorUsername;
    private Set<MultipartFile> images;
}
