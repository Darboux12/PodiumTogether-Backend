package com.podium.controller.news;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.news.NewsRequestDto;
import com.podium.model.dto.response.news.NewsResponseDto;
import com.podium.service.news.NewsService;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(PodiumEndpoint.addNews)
    public ResponseEntity addNews(@RequestBody NewsRequestDto request){

       PodiumValidator.getInstance().validateRequestBody(request);

       if(this.newsService.existNewsByTitle(request.getTitle()))
           throw new ResponseStatusException(
                   HttpStatus.CONFLICT, "News already exist");

       this.newsService.addNews(request);
       return ResponseEntity.ok().body("News successfully added");

    }

    @GetMapping(PodiumEndpoint.findAllNews)
    public ResponseEntity<Iterable<NewsResponseDto>> findAllNews() throws IOException {

        return ResponseEntity
                .status(200)
                .body(this.newsService.findAllNews());

    }

    @GetMapping(PodiumEndpoint.findNewsById)
    public ResponseEntity<NewsResponseDto>
    findNewsById(@PathVariable int id) throws IOException {

        if(this.newsService.existNewsById(id))
            return ResponseEntity
                    .ok()
                    .body(this.newsService.findNewsById(id));

        else throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "News not found");

    }

    @GetMapping(PodiumEndpoint.findNewsByTitle)
    public ResponseEntity<NewsResponseDto>
    findNewsByTitle(@PathVariable String title) throws IOException {

        if(this.newsService.existNewsByTitle(title))
            return ResponseEntity
                    .ok()
                    .body(this.newsService.findNewsByTitle(title));

        else throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "News not found");

    }

    @DeleteMapping(PodiumEndpoint.deleteNewsById)
    public ResponseEntity deleteNewsById(@PathVariable int id) {

        if(!this.newsService.existNewsById(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "News not found");

        this.newsService.deleteNewsById(id);

        return ResponseEntity.ok().body("News successfully deleted");


    }

}

