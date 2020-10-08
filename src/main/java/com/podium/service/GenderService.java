package com.podium.service;

import com.podium.model.entity.Gender;
import com.podium.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Gender findByGenderName(String genderName){
       return this.genderRepository.findByName(genderName);
    }




}
