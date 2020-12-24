package com.podium.service;

import com.podium.dal.entity.Gender;
import com.podium.dal.repository.GenderRepository;
import com.podium.service.dto.request.GenderAddServiceRequest;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GenderService {

    private GenderRepository genderRepository;

    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Transactional
    public void addGender(GenderAddServiceRequest requestDto) throws PodiumEntityAlreadyExistException {

        if(this.genderRepository.existsByGender(requestDto.getGender()))
            throw new PodiumEntityAlreadyExistException("Gender");

        this.genderRepository.save(this.convertServiceAddDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteGenderByName(String name) throws PodiumEntityNotFoundException {

        if(!this.genderRepository.existsByGender(name))
            throw new PodiumEntityNotFoundException("Gender");

        this.genderRepository.deleteByGender(name);
    }

    public boolean existGenderByName(String genderName){
        return this.genderRepository.existsByGender(genderName);
    }

    public Gender findByGenderName(String genderName) throws PodiumEntityNotFoundException {

        return this.genderRepository
                .findByGender(genderName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Gender"));
    }

    public Iterable<Gender> findAllGenders(){
        return this.genderRepository.findAll();
    }

    private Gender convertServiceAddDtoToEntity(GenderAddServiceRequest addServiceDto){
        return new Gender(addServiceDto.getGender());
    }

}
