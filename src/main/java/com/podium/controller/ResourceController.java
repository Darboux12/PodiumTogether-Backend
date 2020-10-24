package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.ResourceImageRequestDto;
import com.podium.service.NewsService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(PodiumEndpoint.addNewsImage)
    public ResponseEntity uploadImageNews(
            @RequestParam("title") String title,
            @RequestParam("images") List<MultipartFile> images) throws IOException {

        ResourceImageRequestDto requestDto = new ResourceImageRequestDto();
        requestDto.setId(title);
        requestDto.setFiles(images);

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        this.newsService.uploadImages(requestDto);

        return ResponseEntity.ok().build();

    }

}
