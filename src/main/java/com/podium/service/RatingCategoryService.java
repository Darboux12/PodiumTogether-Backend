package com.podium.service;

import com.podium.model.dto.request.RatingCategoryRequestDto;
import com.podium.model.dto.response.RatingCategoryResponseDto;
import com.podium.model.entity.City;
import com.podium.model.entity.RatingCategory;
import com.podium.repository.RatingCategoryRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingCategoryService {

    private RatingCategoryRepository ratingCategoryRepository;

    public RatingCategoryService(RatingCategoryRepository ratingCategoryRepository) {
        this.ratingCategoryRepository = ratingCategoryRepository;
    }

    @Transactional
    public void addCategory(RatingCategoryRequestDto requestDto){

        if(this.ratingCategoryRepository.existsByCategory(requestDto.getCategory()))
            throw new PodiumEntityAlreadyExistException("RatingDto Category");

        this.ratingCategoryRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public boolean existCategory(String category){
        return this.ratingCategoryRepository.existsByCategory(category);
    }

    public Iterable<RatingCategory> findAll(){
        return this.ratingCategoryRepository.findAll();
    }

    public RatingCategory findByCategory(String category){

        return this.ratingCategoryRepository
                .findByCategory(category).orElseThrow(() ->
                new PodiumEntityNotFoundException("City"));

    }

    @Transactional
    public void deleteRatingCategory(String category){

        if(!this.ratingCategoryRepository.existsByCategory(category))
            throw new PodiumEntityNotFoundException("RatingDto Category");

        this.ratingCategoryRepository.deleteByCategory(category);
    }

    private RatingCategory convertRequestDtoToEntity(RatingCategoryRequestDto requestDto){
        return new RatingCategory(requestDto.getCategory());
    }

}
