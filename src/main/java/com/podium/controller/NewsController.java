package com.podium.controller;

import com.podium.model.News;
import com.podium.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(value = "/news/add")
    public void addNews(
            @RequestParam("title") String title,
            @RequestParam("shortText") String shortText,
            @RequestParam("linkText") String linkText,
            @RequestParam("fullText") String fullText,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        this.newsService.addNews(title,shortText,linkText,fullText,image);
    }

}

