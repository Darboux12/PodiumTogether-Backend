package com.podium.service;

import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.model.entity.Discipline;
import com.podium.model.entity.Gender;
import com.podium.repository.GenderRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenderService {

    private GenderRepository genderRepository;

    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public boolean existByGenderName(String genderName){
        return this.genderRepository.existsByGender(genderName);
    }

    public Gender findByGenderName(String genderName){

        return this.genderRepository
                .findByGender(genderName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Gender"));
    }

    public Iterable<Gender> findAllGenders(){
        return this.genderRepository.findAll();
    }

    @Transactional
    public void addGender(GenderRequestDto requestDto){

        if(this.genderRepository.existsByGender(requestDto.getGender()))
            throw new PodiumEntityAlreadyExistException("Gender");

        this.genderRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteGenderByName(String name){

        if(!this.genderRepository.existsByGender(name))
            throw new PodiumEntityNotFoundException("Gender");

        this.genderRepository.deleteByGender(name);
    }

    private Gender convertRequestDtoToEntity(GenderRequestDto requestDto){
        return new Gender(requestDto.getGender());
    }

}
