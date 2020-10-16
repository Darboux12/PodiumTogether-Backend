package com.podium.controller;

import com.podium.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResourceController {

    private NewsService newsService;

    @Autowired
    public ResourceController(NewsService newsService) {
        this.newsService = newsService;
    }


    @PostMapping("/image/upload/news")
    public ResponseEntity uploadImageNews(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("title") String title) throws IOException {

        this.newsService.uploadImages(images,title);

        return ResponseEntity.ok().build();



    }


}
