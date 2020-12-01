package com.podium.service.gender;

import com.podium.model.dto.request.gender.GenderRequestDto;
import com.podium.model.dto.response.gender.GenderResponseDto;
import com.podium.model.entity.gender.Gender;
import com.podium.repository.gender.GenderRepository;
import org.springframework.stereotype.Service;

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

    public GenderResponseDto findByGenderName(String genderName){

       return this.convertEntityToResponseDto(
               this.genderRepository.findByGender(genderName));
    }

    public Iterable<GenderResponseDto> findAllGenders(){

        List<GenderResponseDto> responseDtos = new ArrayList<>();

        this.genderRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public void addGender(GenderRequestDto requestDto){
        this.genderRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public void deleteGenderByName(String name){
        this.genderRepository.deleteByGender(name);
    }

    private Gender convertRequestDtoToEntity(GenderRequestDto requestDto){
        return new Gender(requestDto.getGender());
    }

    private GenderResponseDto convertEntityToResponseDto(Gender gender){
        return new GenderResponseDto(gender.getGender());
    }

}
