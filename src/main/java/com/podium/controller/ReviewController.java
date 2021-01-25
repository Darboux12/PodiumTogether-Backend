package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.response.ReviewControllerResponse;
import com.podium.controller.dto.request.ReviewAddControllerRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.ReviewService;
import com.podium.service.dto.request.ReviewAddServiceRequest;
import com.podium.service.dto.other.StarRatingServiceDto;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityNotSameQuantity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // SUBSCRIBER
    @PostMapping(PodiumEndpoint.addReview)
    public ResponseEntity addReview(
            @RequestPart("review") @PodiumValidBody ReviewAddControllerRequest requestDto,
            @RequestPart("images") List<MultipartFile> images, Authentication authentication) throws PodiumEntityNotSameQuantity, PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumAuthorityException {
        this.reviewService.addReview(ControllerRequestConverter.getInstance().convertReviewAddRequestToServiceDto(requestDto,images),authentication.getName());
        return ResponseEntity.ok().build();
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteReviewById)
    public ResponseEntity deleteReviewById(@PathVariable int id,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        this.reviewService.deleteReviewById(id,authentication.getName());

        return ResponseEntity.ok().body("Review successfully deleted");

    }

    // SUBSCRIBER
    @GetMapping(PodiumEndpoint.findReviewsByAuthor)
    public ResponseEntity<Iterable<ReviewControllerResponse>> findAllReviewsByAuthor(@PathVariable String username,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        var reviews = this.reviewService.findAllReviewsByAuthor(username,authentication.getName());

        return ResponseEntity
                .ok()
                .body(ControllerResponseConverter.
                        getInstance()
                        .convertReviewServiceIterableToResponseDto(reviews));

    }

    // SUBSCRIBER
    @PatchMapping(PodiumEndpoint.incrementReviewLikes)
    public ResponseEntity incrementReviewLikesById(@PathVariable int id,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        this.reviewService.incrementReviewLikesById(id,authentication.getName());

        return ResponseEntity.ok().body("Review likes was successfully incremented!");


    }

    // SUBSCRIBER
    @PatchMapping(PodiumEndpoint.incrementReviewDislikes)
    public ResponseEntity incrementReviewDislikesById(@PathVariable int id,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        this.reviewService.incrementReviewDislikesById(id,authentication.getName());

        return ResponseEntity.ok().body("Review likes was successfully incremented!");


    }

}
