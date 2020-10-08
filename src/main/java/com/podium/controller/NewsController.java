package com.podium.controller;

import com.podium.model.entity.News;
import com.podium.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/news/add")
    public ResponseEntity addNews(
            @RequestParam("title") String title,
            @RequestParam("shortText") String shortText,
            @RequestParam("linkText") String linkText,
            @RequestParam("fullText") String fullText,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        if(title.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Title");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Title cannot be empty");
        }

        if(shortText.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Short-Text");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Short-Text cannot be empty");
        }

        if(linkText.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Link-Text");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Link-Text cannot be empty");
        }

        if(fullText.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Full-Text");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Full-Text cannot be empty");
        }









        this.newsService.addNews(title,shortText,linkText,fullText,image);

        return ResponseEntity.ok().body("Hej");
    }

    @GetMapping("/news/find/all")
    public ResponseEntity findAllNews(){

        return ResponseEntity
                .status(200)
                .body(this.newsService.findAllNews());

    }


}

