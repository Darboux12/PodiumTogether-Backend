package com.podium.service;

import com.podium.model.entity.News;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.repository.NewsRepository;
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
    public void addNews(NewsRequestDto request) {

        if(this.newsRepository.existsByTitle(request.getTitle()))
            throw new PodiumEntityAlreadyExistException("News with given title");

        this.newsRepository.save(this.convertRequestDtoToEntity(request));
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

    @Transactional
    public void deleteNewsById(int id){
        if(!this.newsRepository.existsById(id))
            throw new PodiumEntityNotFoundException("News with given id");
        this.newsRepository.deleteById(id);
    }

    private News convertRequestDtoToEntity(NewsRequestDto requestDto){

        return new News(
                requestDto.getTitle(),
                requestDto.getShortText(),
                requestDto.getText(),
                requestDto.getLinkText(),
                new Date()
        );

    }


}
