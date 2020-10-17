package com.podium.service;

import com.podium.configuration.Paths;
import com.podium.model.dto.request.ResourceImageRequestDto;
import com.podium.model.entity.News;
import com.podium.model.entity.PodiumResource;
import com.podium.model.other.PodiumFile;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.NewsResponseDto;
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

    public void addNews(NewsRequestDto request) {

        this.newsRepository.save(this.convertRequestDtoToEntity(request));

    }

    public Iterable<NewsResponseDto> findAllNews() throws IOException {

        List<NewsResponseDto> newsResponseDtos = new ArrayList<>();

        for(News news : this.newsRepository.findAll())
            newsResponseDtos.add(this.convertEntityToResponseDto(news));

        return newsResponseDtos;
}

    public NewsResponseDto findNewsById(int id) throws IOException {

        News news = this.newsRepository.findById(id).orElse(null);

        return news != null ? this.convertEntityToResponseDto(news) : null;

    }

    public NewsResponseDto findNewsByTitle(String title) throws IOException {

        News news = this.newsRepository.findByTitle(title);

        return news != null ? this.convertEntityToResponseDto(news) : null;

    }

    public boolean existNewsById(int id){
        return this.newsRepository.existsById(id);
    }

    public void uploadImages(ResourceImageRequestDto requestDto) throws IOException {

        for (MultipartFile image : requestDto.getFiles()) {

            String path = Paths.getImages() + image.getOriginalFilename();

            File file = new File(path);

            image.transferTo(file);

            PodiumResource resource = new PodiumResource();
            resource.setName(image.getOriginalFilename());
            resource.setType(image.getContentType());
            resource.setPath(path);

            News news = this.newsRepository.findByTitle(requestDto.getId());

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

    private News convertRequestDtoToEntity(NewsRequestDto requestDto){

        News news = new News();
        news.setTitle(requestDto.getTitle());
        news.setShortText(requestDto.getShortText());
        news.setLinkText(requestDto.getLinkText());
        news.setText(requestDto.getText());
        Date date = new Date();
        news.setDate(date);

        return news;

    }

    private NewsResponseDto convertEntityToResponseDto(News news){

        NewsResponseDto responseDto = new NewsResponseDto();
        responseDto.setId(news.getNewsId());
        responseDto.setShortText(news.getShortText());
        responseDto.setLinkText(news.getLinkText());
        responseDto.setText(news.getText());
        responseDto.setDate(news.getDate());
        responseDto.setTitle(news.getTitle());

        for(PodiumResource resource : news.getNewsResources()){

            PodiumFile podiumFile = new PodiumFile();

            try {
                podiumFile.setContent(FileCopyUtils
                        .copyToByteArray(new File(resource.getPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            podiumFile.setName(resource.getName());
            podiumFile.setType(resource.getType());

            responseDto.getPodiumFiles().add(podiumFile);

        }

        return responseDto;

    }
}
