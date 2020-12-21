package com.podium.service;

import com.podium.dal.entity.*;
import com.podium.dal.repository.ReviewRepository;
import com.podium.service.dto.request.ReviewAddServiceDto;
import com.podium.service.dto.other.StarRatingServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityNotSameQuantity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    private UserService userService;

    private StarRatingService starRatingService;
    private ResourceService resourceService;
    private PlaceService placeService;
    private RatingCategoryService ratingCategoryService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService, StarRatingService starRatingService, ResourceService resourceService, PlaceService placeService, RatingCategoryService ratingCategoryService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.starRatingService = starRatingService;
        this.resourceService = resourceService;
        this.placeService = placeService;
        this.ratingCategoryService = ratingCategoryService;
    }

    @Transactional
    public void addReview(ReviewAddServiceDto addServiceDto) throws PodiumEntityNotFoundException, PodiumEntityAlreadyExistException, PodiumEntityNotSameQuantity {

        Place place = this.placeService.findPlaceByName(addServiceDto.getPlace());

        User author = this.userService.findUserByUsername(addServiceDto.getAuthor());

        if(this.reviewRepository.existsByAuthorAndPlace(author,place))
            throw new PodiumEntityAlreadyExistException("User review for given place");

        if(this.ratingCategoryService.countAllRatingCategories() != addServiceDto.getStarRatings().size())
            throw new PodiumEntityNotSameQuantity("Ratings","Rating Categories");

        this.reviewRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));

    }

    @Transactional
    public void deleteReviewById(int id) throws PodiumEntityNotFoundException {

        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Review with given id"));

        this.reviewRepository.deleteById(id);

        this.resourceService.deleteResources(review.getImages());

    }

    public Iterable<Review> findAllReviewsByAuthor(String userName) throws PodiumEntityNotFoundException {

        User author = this.userService.findUserByUsername(userName);

        return this.reviewRepository.findByAuthor(author);
    }

    @Transactional
    public void incrementReviewLikesById(int id) throws PodiumEntityNotFoundException {

        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Review with given id"));

        this.reviewRepository.incrementLikesCountById(review.getId());

    }

    @Transactional
    public void incrementReviewDislikesById(int id) throws PodiumEntityNotFoundException {

        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Review with given id"));

        this.reviewRepository.incrementDislikesCountById(review.getId());

    }

    private Review convertServiceAddDtoToEntity(ReviewAddServiceDto dto) throws PodiumEntityNotFoundException {

        var starRatings = new HashSet<StarRating>();

        for(StarRatingServiceDto rating : dto.getStarRatings())
            starRatings.add(this.starRatingService
                    .getEntity(rating.getCategory(),rating.getRating()));

        User author = this.userService.getEntity(dto.getAuthor());

        Place place = this.placeService.findPlaceByName(dto.getPlace());

        Set<PodiumResource> resources = this.resourceService
                .createPodiumImageResources(dto.getImages());

        return new Review(starRatings, dto.getOpinion(), author, place, resources, 0,0);

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
