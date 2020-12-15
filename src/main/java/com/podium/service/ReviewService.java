package com.podium.service;

import com.podium.dal.repository.ReviewRepository;
import org.springframework.stereotype.Service;



@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    private UserService userService;

    private StarRatingService starRatingService;
    private ResourceService resourceService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService,StarRatingService starRatingService,ResourceService resourceService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.starRatingService = starRatingService;
        this.resourceService = resourceService;
    }
/*
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
*/
}
