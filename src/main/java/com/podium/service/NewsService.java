package com.podium.service;

import com.podium.dal.entity.News;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.User;
import com.podium.dal.repository.NewsRepository;
import com.podium.service.dto.request.NewsAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    private ResourceService resourceService;
    private UserService userService;
    private SecurityService securityService;

    public NewsService(NewsRepository newsRepository, ResourceService resourceService, UserService userService, SecurityService securityService) {
        this.newsRepository = newsRepository;
        this.resourceService = resourceService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Transactional
    public void addNews(NewsAddServiceRequest newsAddServiceRequest, String adminUsername) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(this.newsRepository.existsByTitle(newsAddServiceRequest.getTitle()))
            throw new PodiumEntityAlreadyExistException("News with given title");

        this.newsRepository.save(this.convertServiceAddDtoToEntity(newsAddServiceRequest));
    }

    @Transactional
    public void deleteNewsById(int id, String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("News with given id"));

        this.resourceService.deleteResources(news.getNewsResources());

        this.newsRepository.deleteById(id);
    }

    public Iterable<News> findAllNews(){
        return this.newsRepository.findAll();
    }

    public News findNewsById(int id) throws PodiumEntityNotFoundException {

        return this.newsRepository.findById(id).orElseThrow(() ->
                new PodiumEntityNotFoundException("News with given id"));

    }

    public News findNewsByTitle(String title) throws PodiumEntityNotFoundException {

        return this.newsRepository.findByTitle(title).orElseThrow(() ->
                new PodiumEntityNotFoundException("News with given title"));
    }

    private News convertServiceAddDtoToEntity(NewsAddServiceRequest newsAddServiceRequest){

        Set<PodiumResource> resources = this.resourceService
                        .createPodiumImageResources(newsAddServiceRequest.getImages());

        return new News(
                newsAddServiceRequest.getTitle(),
                newsAddServiceRequest.getShortText(),
                newsAddServiceRequest.getText(),
                newsAddServiceRequest.getLinkText(),
                new Date(),
                resources
        );
    }

}
