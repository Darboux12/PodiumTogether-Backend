package com.podium.service;

import com.podium.configuration.Paths;
import com.podium.model.entity.News;
import com.podium.model.entity.PodiumResource;
import com.podium.model.other.PodiumFile;
import com.podium.model.request.NewsRequest;
import com.podium.model.response.NewsResponse;
import com.podium.repository.NewsRepository;
import com.podium.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private ResourceRepository resourcesRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, ResourceRepository resourcesRepository) {
        this.newsRepository = newsRepository;
        this.resourcesRepository = resourcesRepository;
    }

    public ResponseEntity getAllNews(){

        return ResponseEntity
                .status(200)
                .body(this.newsRepository.findAll());

    }

    public void addNews(NewsRequest request) {

        News news = new News();
        news.setTitle(request.getTitle());
        news.setShortText(request.getShortText());
        news.setLinkText(request.getLinkText());
        news.setText(request.getFullText());
        Date date = new Date();
        news.setDate(date);

        this.newsRepository.save(news);

    }

    public Iterable<NewsResponse> findAllNews() throws IOException {

        List<NewsResponse> newsResponses = new ArrayList<>();

        for(News news : this.newsRepository.findAll()){

            NewsResponse newsResponse = new NewsResponse();
            newsResponse.setId(news.getNewsId());
            newsResponse.setDate(news.getDate());
            newsResponse.setLinkText(news.getLinkText());
            newsResponse.setShortText(news.getShortText());
            newsResponse.setText(news.getText());
            newsResponse.setTitle(news.getTitle());

            for(PodiumResource resource : news.getNewsResources()){

                PodiumFile podiumFile = new PodiumFile();

                podiumFile.setContent(FileCopyUtils
                        .copyToByteArray(new File(resource.getPath())));

                podiumFile.setName(resource.getName());
                podiumFile.setType(resource.getType());

                newsResponse.getPodiumFiles().add(podiumFile);
            }

            newsResponses.add(newsResponse);

        }

        return newsResponses;
}

    public NewsResponse findNewsById(int id) throws IOException {

        News news = this.newsRepository.findById(id).orElse(null);

        if(news != null) {

            NewsResponse newsResponse = new NewsResponse();
            newsResponse.setId(news.getNewsId());
            newsResponse.setDate(news.getDate());
            newsResponse.setLinkText(news.getLinkText());
            newsResponse.setShortText(news.getShortText());
            newsResponse.setText(news.getText());
            newsResponse.setTitle(news.getTitle());

            for (PodiumResource resource : news.getNewsResources()) {

                PodiumFile podiumFile = new PodiumFile();

                podiumFile.setContent(FileCopyUtils
                        .copyToByteArray(new File(resource.getPath())));

                podiumFile.setName(resource.getName());
                podiumFile.setType(resource.getType());

                newsResponse.getPodiumFiles().add(podiumFile);

            }

            return newsResponse;

        }

        return null;

    }

    public NewsResponse findNewsByTitle(String title) throws IOException {

        News news = this.newsRepository.findByTitle(title);

        if(news != null) {

            NewsResponse newsResponse = new NewsResponse();
            newsResponse.setId(news.getNewsId());
            newsResponse.setDate(news.getDate());
            newsResponse.setLinkText(news.getLinkText());
            newsResponse.setShortText(news.getShortText());
            newsResponse.setText(news.getText());
            newsResponse.setTitle(news.getTitle());

            for (PodiumResource resource : news.getNewsResources()) {

                PodiumFile podiumFile = new PodiumFile();

                podiumFile.setContent(FileCopyUtils
                        .copyToByteArray(new File(resource.getPath())));

                podiumFile.setName(resource.getName());
                podiumFile.setType(resource.getType());

                newsResponse.getPodiumFiles().add(podiumFile);

            }

            return newsResponse;

        }

        return null;

    }

    public boolean existNewsById(int id){
        return this.newsRepository.existsById(id);
    }

    public void uploadImages(List<MultipartFile> images, String title) throws IOException {

        for (MultipartFile image : images) {

            String path = Paths.getImages() + image.getOriginalFilename();

            File file = new File(path);

            image.transferTo(file);

            PodiumResource resource = new PodiumResource();
            resource.setName(image.getOriginalFilename());
            resource.setType(image.getContentType());
            resource.setPath(path);

            News news = this.newsRepository.findByTitle(title);

            news.getNewsResources().add(resource);

            resource.getNews().add(news);

            this.resourcesRepository.save(resource);
        }

    }

    public boolean existNewsByTitle(String newsTitle){
        return this.newsRepository.existsByTitle(newsTitle);
    }

    public void deleteNewsById(int id){
        this.newsRepository.deleteById(id);
    }
}
