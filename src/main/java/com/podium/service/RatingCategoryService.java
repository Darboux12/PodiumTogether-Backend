package com.podium.service;

import com.podium.dal.entity.RatingCategory;
import com.podium.dal.entity.User;
import com.podium.dal.repository.RatingCategoryRepository;
import com.podium.service.dto.request.RatingCategoryAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RatingCategoryService {

    private RatingCategoryRepository ratingCategoryRepository;
    private UserService userService;
    private SecurityService securityService;

    public RatingCategoryService(RatingCategoryRepository ratingCategoryRepository, UserService userService, SecurityService securityService) {
        this.ratingCategoryRepository = ratingCategoryRepository;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Transactional
    public void addCategory(RatingCategoryAddServiceRequest ratingCategoryAddServiceRequest, String adminUsername) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(this.ratingCategoryRepository.existsByCategory(ratingCategoryAddServiceRequest.getCategory()))
            throw new PodiumEntityAlreadyExistException("RatingDto Category");

        this.ratingCategoryRepository.save(this.convertServiceAddDtoToEntity(ratingCategoryAddServiceRequest));
    }

    @Transactional
    public void deleteRatingCategoryByCategory(String category,String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(!this.ratingCategoryRepository.existsByCategory(category))
            throw new PodiumEntityNotFoundException("RatingDto Category");

        this.ratingCategoryRepository.deleteByCategory(category);
    }

    @Transactional
    public long countAllRatingCategories(){
        return this.ratingCategoryRepository.count();
    }

    public boolean existCategoryByCategory(String category,String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        User user = userService.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);
        return this.ratingCategoryRepository.existsByCategory(category);
    }

    public Iterable<RatingCategory> findAllCategory(String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        User user = userService.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);
        return this.ratingCategoryRepository.findAll();
    }

    public RatingCategory findCategoryByCategory(String category,String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);

        return this.ratingCategoryRepository
                .findByCategory(category).orElseThrow(() ->
                new PodiumEntityNotFoundException("City"));

    }

    private RatingCategory convertServiceAddDtoToEntity(RatingCategoryAddServiceRequest ratingCategoryAddServiceRequest){
        return new RatingCategory(ratingCategoryAddServiceRequest.getCategory());
    }

    public RatingCategory getEntity(String ratingCategoryName) throws PodiumEntityNotFoundException {

        return this.ratingCategoryRepository
                .findByCategory(ratingCategoryName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Rating Category"));

    }
}
