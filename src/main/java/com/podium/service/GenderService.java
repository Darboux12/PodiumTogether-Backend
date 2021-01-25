package com.podium.service;

import com.podium.dal.entity.Gender;
import com.podium.dal.entity.User;
import com.podium.dal.repository.GenderRepository;
import com.podium.service.dto.request.GenderAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GenderService {

    private GenderRepository genderRepository;

    private UserService userService;
    private SecurityService securityService;

    public GenderService(GenderRepository genderRepository, UserService userService, SecurityService securityService) {
        this.genderRepository = genderRepository;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Transactional
    public void addGender(GenderAddServiceRequest requestDto, String adminUsername) throws PodiumEntityAlreadyExistException, PodiumAuthorityException, PodiumEntityNotFoundException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(this.genderRepository.existsByGender(requestDto.getGender()))
            throw new PodiumEntityAlreadyExistException("Gender");

        this.genderRepository.save(this.convertServiceAddDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteGenderByName(String name, String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

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
