package com.podium.service;

import com.podium.dal.entity.RatingCategory;
import com.podium.dal.repository.RatingCategoryRepository;
import com.podium.service.dto.RatingCategoryAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RatingCategoryService {

    private RatingCategoryRepository ratingCategoryRepository;

    public RatingCategoryService(RatingCategoryRepository ratingCategoryRepository) {
        this.ratingCategoryRepository = ratingCategoryRepository;
    }

    @Transactional
    public void addCategory(RatingCategoryAddServiceDto ratingCategoryAddServiceDto){

        if(this.ratingCategoryRepository.existsByCategory(ratingCategoryAddServiceDto.getCategory()))
            throw new PodiumEntityAlreadyExistException("RatingDto Category");

        this.ratingCategoryRepository.save(this.convertServiceAddDtoToEntity(ratingCategoryAddServiceDto));
    }

    @Transactional
    public void deleteRatingCategoryByCategory(String category){

        if(!this.ratingCategoryRepository.existsByCategory(category))
            throw new PodiumEntityNotFoundException("RatingDto Category");

        this.ratingCategoryRepository.deleteByCategory(category);
    }

    public boolean existCategoryByCategory(String category){
        return this.ratingCategoryRepository.existsByCategory(category);
    }

    public Iterable<RatingCategory> findAllCategory(){
        return this.ratingCategoryRepository.findAll();
    }

    public RatingCategory findCategoryByCategory(String category){

        return this.ratingCategoryRepository
                .findByCategory(category).orElseThrow(() ->
                new PodiumEntityNotFoundException("City"));

    }

    private RatingCategory convertServiceAddDtoToEntity(RatingCategoryAddServiceDto ratingCategoryAddServiceDto){
        return new RatingCategory(ratingCategoryAddServiceDto.getCategory());
    }

}
