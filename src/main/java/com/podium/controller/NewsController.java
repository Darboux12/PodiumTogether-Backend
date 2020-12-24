package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.other.FileControllerDto;
import com.podium.controller.dto.request.NewsAddControllerRequest;
import com.podium.controller.dto.response.NewsControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.News;
import com.podium.service.NewsService;
import com.podium.service.dto.request.NewsAddServiceRequest;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
    protected ResponseEntity addNews(@RequestPart("news") @PodiumValidBody NewsAddControllerRequest addRequest,
                                  @RequestPart("images") List<MultipartFile> images

    ) throws PodiumEntityAlreadyExistException {

       this.newsService.addNews(this.convertAddRequestToServiceDto(addRequest,images));

       return ResponseEntity.ok().body("News successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllNews)
    public ResponseEntity<Iterable<NewsControllerResponse>> findAllNews(){

        var news = this.newsService.findAllNews();
        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsById)
    public ResponseEntity<NewsControllerResponse>
    findNewsById(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        var news = this.newsService.findNewsById(id);
        return ResponseEntity.ok().body(this.convertEntityToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsByTitle)
    public ResponseEntity<NewsControllerResponse>
    findNewsByTitle(@PathVariable @PodiumValidVariable String title) throws PodiumEntityNotFoundException {

        var news = this.newsService.findNewsByTitle(title);
        return ResponseEntity.ok().body(this.convertEntityToResponseDto(news));
    }

    @DeleteMapping(PodiumEndpoint.deleteNewsById)
    public ResponseEntity deleteNewsById(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        this.newsService.deleteNewsById(id);
        return ResponseEntity.ok().body("News successfully deleted");
    }

    private NewsControllerResponse convertEntityToResponseDto(News news){

        return new NewsControllerResponse(
                news.getId(),
                news.getTitle(),
                news.getShortText(),
                news.getText(),
                news.getLinkText(),
                news.getDate(),
                this.findNewsFiles(news)
        );

    }

    private NewsAddServiceRequest convertAddRequestToServiceDto(NewsAddControllerRequest request, List<MultipartFile> images){
        return new NewsAddServiceRequest(
                request.getTitle(),
                request.getShortText(),
                request.getLinkText(),
                request.getText(),
                new HashSet<>(images)
        );
    }

    private Iterable<NewsControllerResponse> convertEntityIterableToResponseDto(Iterable<News> contacts){

        var newsResponses = new ArrayList<NewsControllerResponse>();

        contacts.forEach(x -> newsResponses.add(this.convertEntityToResponseDto(x)));

        return newsResponses;
    }

    private List<FileControllerDto> findNewsFiles(News news){

        List<FileControllerDto> fileControllerDtos = new ArrayList<>();

        news
                .getNewsResources()
                .forEach(x -> {

                    try {
                        fileControllerDtos.add(new FileControllerDto(
                                x.getName(),
                                x.getType(),
                                FileCopyUtils.copyToByteArray(new File(x.getPath()))
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return fileControllerDtos;

    }



}

