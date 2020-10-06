package com.podium.controller;

import com.podium.model.entity.News;
import com.podium.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
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

        this.newsService.addNews(title,shortText,linkText,fullText,image);

        return ResponseEntity.ok().body("Hej");
    }

    @GetMapping("/news/get/all")
    public Iterable<News> findAllNews(){
        return this.newsService.findallNews();
    }


}

