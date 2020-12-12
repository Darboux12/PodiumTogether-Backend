package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.other.PodiumFileDto;
import com.podium.controller.dto.request.NewsAddRequest;
import com.podium.controller.dto.response.NewsResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.News;
import com.podium.service.NewsService;
import com.podium.service.dto.NewsAddServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(PodiumEndpoint.addNews)
    protected ResponseEntity addNews(@RequestPart("news") @PodiumValidBody NewsAddRequest addRequest,
                                  @RequestPart("images") List<MultipartFile> images

    ){

       this.newsService.addNews(this.convertAddRequestToServiceDto(addRequest,images));

       return ResponseEntity.ok().body("News successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllNews)
    public ResponseEntity<Iterable<NewsResponse>> findAllNews(){

        var news = this.newsService.findAllNews();
        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsById)
    public ResponseEntity<NewsResponse>
    findNewsById(@PathVariable @PodiumValidVariable int id){

        var news = this.newsService.findNewsById(id);
        return ResponseEntity.ok().body(this.convertEntityToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsByTitle)
    public ResponseEntity<NewsResponse>
    findNewsByTitle(@PathVariable @PodiumValidVariable String title){

        var news = this.newsService.findNewsByTitle(title);
        return ResponseEntity.ok().body(this.convertEntityToResponseDto(news));
    }

    @DeleteMapping(PodiumEndpoint.deleteNewsById)
    public ResponseEntity deleteNewsById(@PathVariable @PodiumValidVariable int id) {
        this.newsService.deleteNewsById(id);
        return ResponseEntity.ok().body("News successfully deleted");
    }

    private NewsResponse convertEntityToResponseDto(News news){

        return new NewsResponse(
                news.getNewsId(),
                news.getTitle(),
                news.getShortText(),
                news.getText(),
                news.getLinkText(),
                news.getDate(),
                this.findNewsFiles(news)
        );

    }

    private NewsAddServiceDto convertAddRequestToServiceDto(NewsAddRequest request, List<MultipartFile> images){
        return new NewsAddServiceDto(
                request.getTitle(),
                request.getShortText(),
                request.getLinkText(),
                request.getText(),
                new HashSet<>(images)
        );
    }

    private Iterable<NewsResponse> convertEntityIterableToResponseDto(Iterable<News> contacts){

        var newsResponses = new ArrayList<NewsResponse>();

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

