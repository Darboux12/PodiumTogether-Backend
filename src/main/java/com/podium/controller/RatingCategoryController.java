package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.RatingCategoryAddRequest;
import com.podium.controller.dto.response.RatingCategoryResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.RatingCategory;
import com.podium.service.RatingCategoryService;
import com.podium.service.dto.RatingCategoryAddServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class RatingCategoryController {

    private RatingCategoryService ratingCategoryService;

    public RatingCategoryController(RatingCategoryService ratingCategoryService) {
        this.ratingCategoryService = ratingCategoryService;
    }

    @PostMapping(PodiumEndpoint.addRatingCategory)
    public ResponseEntity addRatingCategory(@RequestBody @PodiumValidBody RatingCategoryAddRequest requestDto){
        this.ratingCategoryService.addCategory(this.convertAddRequestToServiceDto(requestDto));
        return ResponseEntity.ok().body("RatingDto category successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllRatingCategories)
    public ResponseEntity<Iterable<RatingCategoryResponse>> findAll(){

        var categories = this.ratingCategoryService.findAllCategory();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(categories));
    }

    @GetMapping(PodiumEndpoint.findRatingCategory)
    public ResponseEntity<RatingCategoryResponse> findCategory(@PathVariable @PodiumValidVariable String category){

           var categoryEntity = this.ratingCategoryService.findCategoryByCategory(category);

           return ResponseEntity.ok().body(this.convertEntityToResponseDto(categoryEntity));
    }

    @GetMapping(PodiumEndpoint.existRatingCategory)
    public ResponseEntity existRatingCategory(@PathVariable @PodiumValidVariable String category){
            return ResponseEntity.ok().body(this.ratingCategoryService.existCategoryByCategory(category));
    }

    @DeleteMapping(PodiumEndpoint.deleteRatingCategory)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String category){
        this.ratingCategoryService.deleteRatingCategoryByCategory(category);
        return ResponseEntity.ok().body("RatingDto category successfully deleted");
    }

    private RatingCategoryAddServiceDto convertAddRequestToServiceDto(RatingCategoryAddRequest request){
        return new RatingCategoryAddServiceDto(request.getCategory());
    }

    private RatingCategoryResponse convertEntityToResponseDto(RatingCategory ratingCategory){
        return new RatingCategoryResponse(ratingCategory.getCategory());
    }

    private Iterable<RatingCategoryResponse> convertEntityIterableToResponseDto(Iterable<RatingCategory> ratingCategories){

        var categoryResponses = new ArrayList<RatingCategoryResponse>();

        ratingCategories.forEach(x -> categoryResponses.add(this.convertEntityToResponseDto(x)));

        return categoryResponses;
    }

}
