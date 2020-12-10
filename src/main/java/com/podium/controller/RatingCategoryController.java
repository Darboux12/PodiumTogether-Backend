package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.RatingCategory;
import com.podium.service.RatingCategoryService;
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
    public ResponseEntity addRatingCategory(@RequestBody @PodiumValidBody RatingCategoryRequestDto requestDto){
        this.ratingCategoryService.addCategory(requestDto);
        return ResponseEntity.ok().body("RatingDto category successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllRatingCategories)
    public ResponseEntity<Iterable<RatingCategoryResponseDto>> findAll(){

        var categories = this.ratingCategoryService.findAll();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(categories));
    }

    @GetMapping(PodiumEndpoint.findRatingCategory)
    public ResponseEntity<RatingCategoryResponseDto> findCategory(@PathVariable @PodiumValidVariable String category){

           var categoryEntity = this.ratingCategoryService.findByCategory(category);

           return ResponseEntity.ok().body(this.convertEntityToResponseDto(categoryEntity));
    }

    @GetMapping(PodiumEndpoint.existRatingCategory)
    public ResponseEntity existRatingCategory(@PathVariable @PodiumValidVariable String category){
            return ResponseEntity.ok().body(this.ratingCategoryService.existCategory(category));
    }

    @DeleteMapping(PodiumEndpoint.deleteRatingCategory)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String category){
        this.ratingCategoryService.deleteRatingCategory(category);
        return ResponseEntity.ok().body("RatingDto category successfully deleted");
    }

    private RatingCategoryResponseDto convertEntityToResponseDto(RatingCategory ratingCategory){
        return new RatingCategoryResponseDto(ratingCategory.getCategory());
    }

    private Iterable<RatingCategoryResponseDto> convertEntityIterableToResponseDto(Iterable<RatingCategory> ratingCategories){

        var categoryResponses = new ArrayList<RatingCategoryResponseDto>();

        ratingCategories.forEach(x -> categoryResponses.add(this.convertEntityToResponseDto(x)));

        return categoryResponses;
    }

}
