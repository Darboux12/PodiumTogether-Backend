package com.podium.service;

import com.podium.dal.entity.*;
import com.podium.dal.repository.ReviewRepository;
import com.podium.service.dto.converter.ServiceConverter;
import com.podium.service.dto.other.ReviewServiceDto;
import com.podium.service.dto.request.ReviewAddServiceRequest;
import com.podium.service.dto.other.StarRatingServiceDto;
import com.podium.service.exception.PodiumAuthorityException;
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
    private SecurityService securityService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService, StarRatingService starRatingService, ResourceService resourceService, PlaceService placeService, RatingCategoryService ratingCategoryService, SecurityService securityService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.starRatingService = starRatingService;
        this.resourceService = resourceService;
        this.placeService = placeService;
        this.ratingCategoryService = ratingCategoryService;
        this.securityService = securityService;
    }

    @Transactional
    public void addReview(ReviewAddServiceRequest addServiceDto,String userName) throws PodiumEntityNotFoundException, PodiumEntityAlreadyExistException, PodiumEntityNotSameQuantity, PodiumAuthorityException {

        User user = userService.getEntity(userName);
        this.securityService.validateUserSubscriberAuthority(user);

        Place place = this.placeService.findPlaceEntityByName(addServiceDto.getPlace());

        User author = this.userService.getEntity(addServiceDto.getAuthor());

        if(this.reviewRepository.existsByAuthorAndPlace(author,place))
            throw new PodiumEntityAlreadyExistException("User review for given place");

        if(this.ratingCategoryService.countAllRatingCategories() != addServiceDto.getStarRatings().size())
            throw new PodiumEntityNotSameQuantity("Ratings","Rating Categories");

        this.reviewRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));

    }

    @Transactional
    public void deleteReviewById(int id, String adminName) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(adminName);
        this.securityService.validateUserAdminOrModeratorAuthority(user);

        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Review with given id"));

        this.reviewRepository.deleteById(id);

        this.resourceService.deleteResources(review.getImages());

    }

    public Iterable<ReviewServiceDto> findAllReviewsByAuthor(String authorName,String userName) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(userName);
        this.securityService.validateUserSubscriberAuthority(user);

        User author = this.userService.getEntity(authorName);

        return ServiceConverter
                .getInstance()
                .convertReviewIterableToDto(this.reviewRepository.findByAuthor(author));

    }

    @Transactional
    public void incrementReviewLikesById(int id,String userName) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(userName);
        this.securityService.validateUserSubscriberAuthority(user);

        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Review with given id"));

        this.reviewRepository.incrementLikesCountById(review.getId());

    }

    @Transactional
    public void incrementReviewDislikesById(int id,String userName) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(userName);
        this.securityService.validateUserSubscriberAuthority(user);

        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Review with given id"));

        this.reviewRepository.incrementDislikesCountById(review.getId());

    }

    private Review convertServiceAddDtoToEntity(ReviewAddServiceRequest dto) throws PodiumEntityNotFoundException {

        var starRatings = new HashSet<StarRating>();

        for(StarRatingServiceDto rating : dto.getStarRatings())
            starRatings.add(this.starRatingService
                    .getEntity(rating.getCategory(),rating.getRating()));

        User author = this.userService.getEntity(dto.getAuthor());

        Place place = this.placeService.findPlaceEntityByName(dto.getPlace());

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
