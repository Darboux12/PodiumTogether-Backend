package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerConverter;
import com.podium.controller.dto.response.ReviewControllerResponse;
import com.podium.controller.dto.request.ReviewAddControllerRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Review;
import com.podium.service.ReviewService;
import com.podium.service.dto.request.ReviewAddServiceRequest;
import com.podium.service.dto.other.StarRatingServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityNotSameQuantity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
            @RequestPart("review") @PodiumValidBody ReviewAddControllerRequest requestDto,
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
    public ResponseEntity<Iterable<ReviewControllerResponse>> findAllReviewsByAuthor(@PathVariable String username) throws PodiumEntityNotFoundException {

        var reviews = this.reviewService.findAllReviewsByAuthor(username);

        return ResponseEntity
                .ok()
                .body(ControllerConverter.
                        getInstance()
                        .convertReviewServiceIterableToResponseDto(reviews));

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

    private ReviewAddServiceRequest convertAddRequestToServiceDto(ReviewAddControllerRequest addRequest, List<MultipartFile> images){

        var ratingDtos = new HashSet<StarRatingServiceDto>();

        addRequest.getStarRatings().forEach(rating ->
                ratingDtos.add(new StarRatingServiceDto(rating.getCategory(), rating.getRating()
        )));


        return new ReviewAddServiceRequest(
                addRequest.getAuthor(),
                addRequest.getPlace(),
                ratingDtos,
                addRequest.getOpinion(),
                new HashSet<>(images)
        );


    }



}
