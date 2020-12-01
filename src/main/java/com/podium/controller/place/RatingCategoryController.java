package com.podium.controller.place;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.place.RatingCategoryRequestDto;
import com.podium.model.dto.response.place.RatingCategoryResponseDto;
import com.podium.service.place.RatingCategoryService;
import com.podium.validation.exception.PodiumAlreadyExistException;
import com.podium.validation.exception.PodiumNotExistException;
import com.podium.validation.exception.PodiumNotFoundException;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            throw new PodiumAlreadyExistException("Rating Category");

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

        else throw new PodiumNotFoundException("Rating Category");
    }

    @GetMapping(PodiumEndpoint.existRatingCategory)
    public ResponseEntity existRatingCategory(@PathVariable String category){

        if(this.ratingCategoryService.existCategory(category))
            return ResponseEntity.ok().build();

        else
            throw new PodiumNotExistException("Rating Category");
    }

    @DeleteMapping(PodiumEndpoint.deleteRatingCategory)
    public ResponseEntity deleteCityByName(@PathVariable String category){

        if(!this.ratingCategoryService.existCategory(category))
            throw new PodiumNotFoundException("Rating Category");

        this.ratingCategoryService.deleteRatingCategory(category);

        return ResponseEntity.ok().body("Rating category successfully deleted");
    }

}
