package com.podium.service;

import com.podium.configuration.Paths;
import com.podium.model.entity.News;
import com.podium.model.entity.PodiumResource;
import com.podium.repository.NewsRepository;
import com.podium.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private ResourcesRepository resourcesRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, ResourcesRepository resourcesRepository) {
        this.newsRepository = newsRepository;
        this.resourcesRepository = resourcesRepository;
    }

    public ResponseEntity getAllNews(){

        return ResponseEntity
                .status(200)
                .body(this.newsRepository.findAll());

    }

    public void addNews(
            String title,
            String shortText,
            String linkText,
            String fullText,
            List<MultipartFile> images
            ) throws IOException {

        News news = new News();
        news.setTitle(title);
        news.setShortText(shortText);
        news.setLinkText(linkText);
        news.setText(fullText);
        Date date = new Date();
        news.setDate(date);

        this.newsRepository.save(news);

        for(MultipartFile image : images){

            String path = Paths.getImages() + image.getOriginalFilename();

            File file = new File(path);

            image.transferTo(file);

            PodiumResource resource = new PodiumResource();
            resource.setName(image.getOriginalFilename());
            resource.setType(image.getContentType());
            resource.setPath(path);
            resource.setNews(news);

            this.resourcesRepository.save(resource);
        }

    }

    public Iterable<News> findAllNews(){
        return this.newsRepository.findAll();
    }



}
