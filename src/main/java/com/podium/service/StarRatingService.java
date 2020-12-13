package com.podium.service;

import com.podium.dal.entity.RatingCategory;
import com.podium.dal.entity.StarRating;
import com.podium.dal.repository.RatingRepository;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StarRatingService {

    private RatingRepository ratingRepository;

    private RatingCategoryService ratingCategoryService;

    public StarRatingService(RatingRepository ratingRepository, RatingCategoryService ratingCategoryService) {
        this.ratingRepository = ratingRepository;
        this.ratingCategoryService = ratingCategoryService;
    }

    @Transactional
    public void deleteRating(StarRating rating){

        RatingCategory category =
                this.ratingCategoryService.findCategoryByCategory(rating.getCategory().getCategory());

        if(!this.ratingRepository.existsByCategoryAndRating(category,rating.getRating()))
            throw new PodiumEntityNotFoundException("Rating");

        if(this.isRatingConnectedOnlyToOneReview(rating))
            this.ratingRepository.delete(rating);

    }

    public StarRating getEntity(String categoryName, int rating){

        RatingCategory category =
                this.ratingCategoryService.findCategoryByCategory(categoryName);

        return this.ratingRepository.findByCategoryAndRating(category,rating)
                .orElse(new StarRating(category,rating));

    }

    private boolean isRatingConnectedOnlyToOneReview(StarRating rating){
        return rating.getReviews().size() == 1;
    }


}
