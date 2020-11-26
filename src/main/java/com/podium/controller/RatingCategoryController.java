package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.service.RatingCategoryService;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RatingCategoryController {

    private RatingCategoryService ratingCategoryService;

    public RatingCategoryController(RatingCategoryService ratingCategoryService) {
        this.ratingCategoryService = ratingCategoryService;
    }

    @PostMapping(PodiumEndpoint.addRatingCategory)
    public ResponseEntity addRatingCategory(@RequestBody RatingCategoryRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.ratingCategoryService.existCategory(requestDto.getCategory()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Rating category already exists");

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

        else throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "City with given name cannot be found");
    }

    @GetMapping(PodiumEndpoint.existRatingCategory)
    public ResponseEntity existRatingCategory(@PathVariable String category){

        if(this.ratingCategoryService.existCategory(category))
            return ResponseEntity.ok().build();

        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rating category with given name does not exist");
    }

    @DeleteMapping(PodiumEndpoint.deleteRatingCategory)
    public ResponseEntity deleteCityByName(@PathVariable String category){

        if(!this.ratingCategoryService.existCategory(category))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rating category not found");

        this.ratingCategoryService.deleteRatingCategory(category);

        return ResponseEntity.ok().body("Rating category successfully deleted");
    }

}
