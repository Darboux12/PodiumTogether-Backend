package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.NewsResponseDto;
import com.podium.service.NewsService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.text.ParseException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(PodiumEndpoint.addNews)
    public ResponseEntity addNews(@RequestBody NewsRequestDto request) throws IllegalAccessException, ParseException {

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
    public ResponseEntity<NewsResponseDto> findNewsById(@PathVariable int id) throws IOException {

        if(!this.newsService.existNewsById(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "News not found");

        return ResponseEntity
                .ok()
                .body(this.newsService.findNewsById(id));

    }

    @GetMapping(PodiumEndpoint.findNewsByTitle)
    public ResponseEntity<NewsResponseDto> findNewsByTitle(@PathVariable String title) throws IOException {

        if(!this.newsService.existNewsByTitle(title))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "News not found");

        return ResponseEntity
                .ok()
                .body(this.newsService.findNewsByTitle(title));

    }

    @DeleteMapping(PodiumEndpoint.deleteNewsById)
    public ResponseEntity deleteNewsById(@PathVariable int id) throws IOException {

        if(!this.newsService.existNewsById(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "News not found");

        this.newsService.deleteNewsById(id);

        return ResponseEntity.ok().body("News successfully deleted");


    }

}

