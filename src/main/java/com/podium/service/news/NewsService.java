package com.podium.service.news;

import com.podium.model.entity.news.News;
import com.podium.model.dto.other.PodiumFile;
import com.podium.model.dto.request.news.NewsRequestDto;
import com.podium.model.dto.response.news.NewsResponseDto;
import com.podium.repository.news.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void addNews(NewsRequestDto request) {
        this.newsRepository.save(this.convertRequestDtoToEntity(request));
    }

    public Iterable<NewsResponseDto> findAllNews(){

        List<NewsResponseDto> responseDtos = new ArrayList<>();

        this.newsRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
}

    public NewsResponseDto findNewsById(int id) throws IOException {

        return
                this.convertEntityToResponseDto(Objects.requireNonNull
                        (this.newsRepository.findById(id).orElse(null)));

    }

    public NewsResponseDto findNewsByTitle(String title) throws IOException {

        return this.convertEntityToResponseDto(
                this.newsRepository.findByTitle(title));
    }

    public NewsResponseDto findNewsByDate(Date date){

        return this.convertEntityToResponseDto(
                this.newsRepository.findByDate(date));

    }

    public boolean existNewsById(int id){
        return this.newsRepository.existsById(id);
    }

    public boolean existNewsByTitle(String newsTitle){
        return this.newsRepository.existsByTitle(newsTitle);
    }

    public boolean existNewsByDate(Date date){
        return this.newsRepository.existsByDate(date);
    }

    public void deleteNewsById(int id){
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

    private NewsResponseDto convertEntityToResponseDto(News news){

        List<PodiumFile> podiumFiles = new ArrayList<>();

        news
                .getNewsResources()
                .forEach(x -> {

                        try {

                            podiumFiles.add(new PodiumFile(
                                    x.getName(),
                                    x.getType(),
                                    FileCopyUtils.copyToByteArray(new File(x.getPath()))
                            ));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                });

        return new NewsResponseDto(
                news.getNewsId(),
                news.getTitle(),
                news.getShortText(),
                news.getText(),
                news.getLinkText(),
                news.getDate(),
                podiumFiles
        );

    }
}
