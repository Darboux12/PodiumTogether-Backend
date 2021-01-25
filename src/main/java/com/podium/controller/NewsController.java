package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.other.FileControllerDto;
import com.podium.controller.dto.request.NewsAddControllerRequest;
import com.podium.controller.dto.response.NewsControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.News;
import com.podium.service.NewsService;
import com.podium.service.dto.request.NewsAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    // ADMIN
    @PostMapping(PodiumEndpoint.addNews)
    protected ResponseEntity addNews(@RequestPart("news") @PodiumValidBody NewsAddControllerRequest addRequest,
                                  @RequestPart("images") List<MultipartFile> images, Authentication authentication

    ) throws PodiumEntityAlreadyExistException, PodiumAuthorityException, PodiumEntityNotFoundException {
       this.newsService.addNews(ControllerRequestConverter.getInstance().convertNewsAddRequestToServiceDto(addRequest,images),authentication.getName());
       return ResponseEntity.ok().body("News successfully added");
    }

    @GetMapping(PodiumEndpoint.findAllNews)
    public ResponseEntity<Iterable<NewsControllerResponse>> findAllNews(){
        var news = this.newsService.findAllNews();
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertNewsEntityIterableToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsById)
    public ResponseEntity<NewsControllerResponse>
    findNewsById(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {
        var news = this.newsService.findNewsById(id);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertNewsEntityToResponseDto(news));
    }

    @GetMapping(PodiumEndpoint.findNewsByTitle)
    public ResponseEntity<NewsControllerResponse>
    findNewsByTitle(@PathVariable @PodiumValidVariable String title) throws PodiumEntityNotFoundException {
        var news = this.newsService.findNewsByTitle(title);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertNewsEntityToResponseDto(news));
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteNewsById)
    public ResponseEntity deleteNewsById(@PathVariable @PodiumValidVariable int id, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        this.newsService.deleteNewsById(id,authentication.getName());
        return ResponseEntity.ok().body("News successfully deleted");
    }

}

