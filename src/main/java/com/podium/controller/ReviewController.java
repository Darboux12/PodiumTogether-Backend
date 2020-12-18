package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.PodiumConverter;
import com.podium.controller.dto.other.RatingDto;
import com.podium.controller.dto.other.ReviewResponse;
import com.podium.controller.dto.request.ReviewAddRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Review;
import com.podium.service.ReviewService;
import com.podium.service.dto.ReviewAddServiceDto;
import com.podium.service.dto.StarRatingServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityNotSameQuantity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(PodiumEndpoint.addReview)
    public ResponseEntity addReview(
            @RequestPart("review") @PodiumValidBody ReviewAddRequest requestDto,
            @RequestPart("images") List<MultipartFile> images) throws PodiumEntityNotSameQuantity, PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {

        this.reviewService.addReview(this.convertAddRequestToServiceDto(requestDto,images));

        return ResponseEntity.ok().build();

    }

    @DeleteMapping(PodiumEndpoint.deleteReviewById)
    public ResponseEntity deleteReviewById(@PathVariable int id) throws PodiumEntityNotFoundException {

        this.reviewService.deleteReviewById(id);

        return ResponseEntity.ok().body("Review successfully deleted");

    }

    @GetMapping(PodiumEndpoint.findReviewsByAuthor)
    public ResponseEntity<Iterable<ReviewResponse>> findAllReviewsByAuthor(@PathVariable String username) throws PodiumEntityNotFoundException {

        var reviews = this.reviewService.findAllReviewsByAuthor(username);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(reviews));

    }

    @PatchMapping(PodiumEndpoint.incrementReviewLikes)
    public ResponseEntity incrementReviewLikesById(@PathVariable int id) throws PodiumEntityNotFoundException {

        this.reviewService.incrementReviewLikesById(id);

        return ResponseEntity.ok().body("Review likes was successfully incremented!");


    }

    @PatchMapping(PodiumEndpoint.incrementReviewDislikes)
    public ResponseEntity incrementReviewDislikesById(@PathVariable int id) throws PodiumEntityNotFoundException {

        this.reviewService.incrementReviewDislikesById(id);

        return ResponseEntity.ok().body("Review likes was successfully incremented!");


    }

    private ReviewAddServiceDto convertAddRequestToServiceDto(ReviewAddRequest addRequest, List<MultipartFile> images){

        var ratingDtos = new HashSet<StarRatingServiceDto>();

        addRequest.getStarRatings().forEach(rating ->
                ratingDtos.add(new StarRatingServiceDto(rating.getCategory(), rating.getRating()
        )));


        return new ReviewAddServiceDto(
                addRequest.getAuthor(),
                addRequest.getPlace(),
                ratingDtos,
                addRequest.getOpinion(),
                new HashSet<>(images)
        );


    }

    private Iterable<ReviewResponse> convertEntityIterableToResponseDto(Iterable<Review> reviews){

        var reviewResponses = new ArrayList<ReviewResponse>();

        reviews.forEach(x -> reviewResponses
                .add(PodiumConverter.
                        getInstance().convertReviewToResponseDto(x))
        );

        return reviewResponses;
    }

}
