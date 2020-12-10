package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.other.PodiumFileDto;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.NewsResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.News;
import com.podium.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(PodiumEndpoint.addNews)
    public ResponseEntity addNews(@RequestBody @PodiumValidBody NewsRequestDto request){
       this.newsService.addNews(request);
       return ResponseEntity.ok().body("News successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllNews)
    public ResponseEntity<Iterable<NewsResponseDto>> findAllNews(){

        var news = this.newsService.findAllNews();
        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsById)
    public ResponseEntity<NewsResponseDto>
    findNewsById(@PathVariable @PodiumValidVariable int id){

        var news = this.newsService.findNewsById(id);
        return ResponseEntity.ok().body(this.convertEntityToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsByTitle)
    public ResponseEntity<NewsResponseDto>
    findNewsByTitle(@PathVariable @PodiumValidVariable String title){

        var news = this.newsService.findNewsByTitle(title);
        return ResponseEntity.ok().body(this.convertEntityToResponseDto(news));
    }

    @DeleteMapping(PodiumEndpoint.deleteNewsById)
    public ResponseEntity deleteNewsById(@PathVariable @PodiumValidVariable int id) {
        this.newsService.deleteNewsById(id);
        return ResponseEntity.ok().body("News successfully deleted");
    }

    private NewsResponseDto convertEntityToResponseDto(News news){

        return new NewsResponseDto(
                news.getNewsId(),
                news.getTitle(),
                news.getShortText(),
                news.getText(),
                news.getLinkText(),
                news.getDate(),
                this.findNewsFiles(news)
        );

    }

    private Iterable<NewsResponseDto> convertEntityIterableToResponseDto(Iterable<News> contacts){

        var newsResponses = new ArrayList<NewsResponseDto>();

        contacts.forEach(x -> newsResponses.add(this.convertEntityToResponseDto(x)));

        return newsResponses;
    }

    private List<PodiumFileDto> findNewsFiles(News news){

        List<PodiumFileDto> podiumFileDtos = new ArrayList<>();

        news
                .getNewsResources()
                .forEach(x -> {

                    try {
                        podiumFileDtos.add(new PodiumFileDto(
                                x.getName(),
                                x.getType(),
                                FileCopyUtils.copyToByteArray(new File(x.getPath()))
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return podiumFileDtos;

    }

}

