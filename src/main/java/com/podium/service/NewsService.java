package com.podium.service;

import com.podium.dal.entity.News;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.repository.NewsRepository;
import com.podium.service.dto.NewsAddServiceDto;
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

    public NewsService(NewsRepository newsRepository, ResourceService resourceService) {
        this.newsRepository = newsRepository;
        this.resourceService = resourceService;
    }

    @Transactional
    public void addNews(NewsAddServiceDto newsAddServiceDto) {

        if(this.newsRepository.existsByTitle(newsAddServiceDto.getTitle()))
            throw new PodiumEntityAlreadyExistException("News with given title");

        this.newsRepository.save(this.convertServiceAddDtoToEntity(newsAddServiceDto));
    }

    @Transactional
    public void deleteNewsById(int id){

        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("News with given id"));

        this.resourceService.deleteResources(news.getNewsResources());

        this.newsRepository.deleteById(id);
    }

    public Iterable<News> findAllNews(){
        return this.newsRepository.findAll();
    }

    public News findNewsById(int id) {

        return this.newsRepository.findById(id).orElseThrow(() ->
                new PodiumEntityNotFoundException("News with given id"));

    }

    public News findNewsByTitle(String title) {

        return this.newsRepository.findByTitle(title).orElseThrow(() ->
                new PodiumEntityNotFoundException("News with given title"));
    }

    private News convertServiceAddDtoToEntity(NewsAddServiceDto newsAddServiceDto){

        Set<PodiumResource> resources = this.resourceService
                        .createPodiumImageResources(newsAddServiceDto.getImages());

        return new News(
                newsAddServiceDto.getTitle(),
                newsAddServiceDto.getShortText(),
                newsAddServiceDto.getText(),
                newsAddServiceDto.getLinkText(),
                new Date(),
                resources
        );
    }

}
