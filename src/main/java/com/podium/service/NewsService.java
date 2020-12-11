package com.podium.service;

import com.podium.dal.entity.News;
import com.podium.dal.repository.NewsRepository;
import com.podium.service.dto.NewsAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void addNews(NewsAddServiceDto newsAddServiceDto) {

        if(this.newsRepository.existsByTitle(newsAddServiceDto.getTitle()))
            throw new PodiumEntityAlreadyExistException("News with given title");

        this.newsRepository.save(this.convertServiceAddDtoToEntity(newsAddServiceDto));
    }

    @Transactional
    public void deleteNewsById(int id){
        if(!this.newsRepository.existsById(id))
            throw new PodiumEntityNotFoundException("News with given id");
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

    public boolean existNewsById(int id){
        return this.newsRepository.existsById(id);
    }

    public boolean existNewsByTitle(String newsTitle){
        return this.newsRepository.existsByTitle(newsTitle);
    }

    private News convertServiceAddDtoToEntity(NewsAddServiceDto newsAddServiceDto){

        return new News(
                newsAddServiceDto.getTitle(),
                newsAddServiceDto.getShortText(),
                newsAddServiceDto.getText(),
                newsAddServiceDto.getLinkText(),
                new Date()
        );

    }

}
