package com.podium.service;

import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.model.entity.Gender;
import com.podium.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderService {

    private GenderRepository genderRepository;

    @Autowired
    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public boolean existByGenderName(String genderName){
        return this.genderRepository.existsByName(genderName);
    }

    public GenderResponseDto findByGenderName(String genderName){

       return this.convertEntityToResponseDto(
               this.genderRepository.findByName(genderName));
    }

    public Iterable<GenderResponseDto> findAllGenders(){

        List<GenderResponseDto> responseDtos = new ArrayList<>();

        for(Gender gender : this.genderRepository.findAll())
            responseDtos.add(this.convertEntityToResponseDto(gender));

        return responseDtos;
    }

    public void addGender(GenderRequestDto requestDto){
        this.genderRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public void deleteGenderByName(String name){
        this.genderRepository.deleteByName(name);
    }

    private Gender convertRequestDtoToEntity(GenderRequestDto requestDto){

        Gender gender = new Gender();
        gender.setName(requestDto.getGender());
        return gender;

    }

    private GenderResponseDto convertEntityToResponseDto(Gender gender){

        GenderResponseDto responseDto = new GenderResponseDto();
        responseDto.setId(gender.getGenderId());
        responseDto.setGender(gender.getName());
        return responseDto;

    }






}
