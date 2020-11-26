package com.podium.service;

import com.podium.model.dto.other.Rating;
import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.model.entity.place.RatingCategory;
import com.podium.repository.RatingCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingCategoryService {

    private RatingCategoryRepository ratingCategoryRepository;

    public RatingCategoryService(RatingCategoryRepository ratingCategoryRepository) {
        this.ratingCategoryRepository = ratingCategoryRepository;
    }

    public void addCategory(RatingCategoryRequestDto requestDto){

        this.ratingCategoryRepository.save(
                this.convertRequestDtoToEntity(requestDto));

    }

    public boolean existCategory(String category){
        return this.ratingCategoryRepository.existsByCategory(category);
    }

    public Iterable<RatingCategoryResponseDto> findAll(){

        List<RatingCategoryResponseDto> responseDtos = new ArrayList<>();

        this.ratingCategoryRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;

    }

    public RatingCategoryResponseDto findByCategory(String category){
        return this.convertEntityToResponseDto(
                this.ratingCategoryRepository.findByCategory(category));
    }

    public void deleteRatingCategory(String category){
        this.ratingCategoryRepository.deleteByCategory(category);
    }

    private RatingCategory convertRequestDtoToEntity(RatingCategoryRequestDto requestDto){
        return  new RatingCategory(requestDto.getCategory());
    }

    private RatingCategoryResponseDto convertEntityToResponseDto(RatingCategory ratingCategory){
        return new RatingCategoryResponseDto(ratingCategory.getCategory());
    }

}
