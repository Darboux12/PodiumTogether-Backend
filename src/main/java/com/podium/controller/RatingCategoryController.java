package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.RatingCategoryAddControllerRequest;
import com.podium.controller.dto.response.RatingCategoryControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.RatingCategory;
import com.podium.service.RatingCategoryService;
import com.podium.service.dto.request.RatingCategoryAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
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
    public ResponseEntity addRatingCategory(@RequestBody @PodiumValidBody RatingCategoryAddControllerRequest requestDto) throws PodiumEntityAlreadyExistException {
        this.ratingCategoryService.addCategory(this.convertAddRequestToServiceDto(requestDto));
        return ResponseEntity.ok().body("RatingDto category successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllRatingCategories)
    public ResponseEntity<Iterable<RatingCategoryControllerResponse>> findAll(){

        var categories = this.ratingCategoryService.findAllCategory();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(categories));
    }

    @GetMapping(PodiumEndpoint.findRatingCategory)
    public ResponseEntity<RatingCategoryControllerResponse> findCategory(@PathVariable @PodiumValidVariable String category) throws PodiumEntityNotFoundException {

           var categoryEntity = this.ratingCategoryService.findCategoryByCategory(category);

           return ResponseEntity.ok().body(this.convertEntityToResponseDto(categoryEntity));
    }

    @GetMapping(PodiumEndpoint.existRatingCategory)
    public ResponseEntity existRatingCategory(@PathVariable @PodiumValidVariable String category){
            return ResponseEntity.ok().body(this.ratingCategoryService.existCategoryByCategory(category));
    }

    @DeleteMapping(PodiumEndpoint.deleteRatingCategory)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String category) throws PodiumEntityNotFoundException {
        this.ratingCategoryService.deleteRatingCategoryByCategory(category);
        return ResponseEntity.ok().body("RatingDto category successfully deleted");
    }

    private RatingCategoryAddServiceDto convertAddRequestToServiceDto(RatingCategoryAddControllerRequest request){
        return new RatingCategoryAddServiceDto(request.getCategory());
    }

    private RatingCategoryControllerResponse convertEntityToResponseDto(RatingCategory ratingCategory){
        return new RatingCategoryControllerResponse(ratingCategory.getCategory());
    }

    private Iterable<RatingCategoryControllerResponse> convertEntityIterableToResponseDto(Iterable<RatingCategory> ratingCategories){

        var categoryResponses = new ArrayList<RatingCategoryControllerResponse>();

        ratingCategories.forEach(x -> categoryResponses.add(this.convertEntityToResponseDto(x)));

        return categoryResponses;
    }

}
