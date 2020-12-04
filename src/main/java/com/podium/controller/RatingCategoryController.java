package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import com.podium.service.RatingCategoryService;
import com.podium.controller.response.PodiumAlreadyExistWebException;
import com.podium.controller.response.PodiumNotExistWebException;
import com.podium.controller.response.PodiumNotFoundWebException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingCategoryController {

    private RatingCategoryService ratingCategoryService;
    private PodiumDtoValidator dtoValidator;

    public RatingCategoryController(RatingCategoryService ratingCategoryService, PodiumDtoValidator dtoValidator) {
        this.ratingCategoryService = ratingCategoryService;
        this.dtoValidator = dtoValidator;
    }

    @PostMapping(PodiumEndpoint.addRatingCategory)
    public ResponseEntity addRatingCategory(@RequestBody RatingCategoryRequestDto requestDto){

        try {
            dtoValidator.validateRequestBody(requestDto);
        } catch (PodiumEmptyTextException e) {
            e.printStackTrace();
        }

        if(this.ratingCategoryService.existCategory(requestDto.getCategory()))
            throw new PodiumAlreadyExistWebException("Rating Category");

        this.ratingCategoryService.addCategory(requestDto);

        return ResponseEntity.ok().body("Rating category successfully added");

    }

    @GetMapping(PodiumEndpoint.findAllRatingCategories)
    public Iterable<RatingCategoryResponseDto> findAll(){
        return this.ratingCategoryService.findAll();
    }

    @GetMapping(PodiumEndpoint.findRatingCategory)
    public ResponseEntity<RatingCategoryResponseDto> findCategory(@PathVariable String category){

        if(this.ratingCategoryService.existCategory(category))
            return ResponseEntity.ok().body(this.ratingCategoryService.findByCategory(category));

        else throw new PodiumNotFoundWebException("Rating Category");
    }

    @GetMapping(PodiumEndpoint.existRatingCategory)
    public ResponseEntity existRatingCategory(@PathVariable String category){

        if(this.ratingCategoryService.existCategory(category))
            return ResponseEntity.ok().build();

        else
            throw new PodiumNotExistWebException("Rating Category");
    }

    @DeleteMapping(PodiumEndpoint.deleteRatingCategory)
    public ResponseEntity deleteCityByName(@PathVariable String category){

        if(!this.ratingCategoryService.existCategory(category))
            throw new PodiumNotFoundWebException("Rating Category");

        this.ratingCategoryService.deleteRatingCategory(category);

        return ResponseEntity.ok().body("Rating category successfully deleted");
    }

}
