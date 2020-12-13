package com.podium.service;

import com.podium.dal.entity.*;
import com.podium.dal.repository.ReviewRepository;
import com.podium.service.dto.ReviewServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    private UserService userService;
    private PlaceService placeService;
    private StarRatingService starRatingService;
    private ResourceService resourceService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService, PlaceService placeService,StarRatingService starRatingService,ResourceService resourceService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.placeService = placeService;
        this.starRatingService = starRatingService;
        this.resourceService = resourceService;
    }

    public Review getEntity(String placeName, ReviewServiceDto reviewDto){

        User user = this.userService.getEntity(reviewDto.getAuthorUsername());

        Place place = this.placeService.getEntity(placeName);

        if(this.reviewRepository.existsByAuthorAndAndPlace(user,place))
            throw new PodiumEntityAlreadyExistException("User review for given place");

        Set<StarRating> starRatings = new HashSet<>();

        reviewDto.getStarRatingServiceDtos()
                .forEach(rating -> starRatings.add(this.starRatingService.getEntity(rating.getCategory(), rating.getRating())));

        Set<PodiumResource> resources = this.resourceService
                .createPodiumImageResources(reviewDto.getImages());

        return new Review(starRatings, reviewDto.getOpinion(),user,
                place, resources, 0, 0);

    }

}
