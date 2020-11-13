package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.ResourceRequestDto;
import com.podium.service.ResourceService;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResourceController {

    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(PodiumEndpoint.addNewsImage)
    public ResponseEntity uploadImageNews(
            @RequestParam("title") String title,
            @RequestParam("images") List<MultipartFile> images) throws IOException {

        this.resourceService.uploadNewsImages(
                this.createUploadRequestDto(title,images));

        return ResponseEntity.ok().build();

    }

    @PostMapping(PodiumEndpoint.uploadUserProfileImage)
    public ResponseEntity uploadImageProfile(
            @RequestParam("username") String username,
            @RequestParam("image") List<MultipartFile> images) throws IOException {

        this.resourceService.uploadUserProfileImages(
                this.createUploadRequestDto(username,images));

        return ResponseEntity.ok().build();

    }

    @PostMapping(PodiumEndpoint.uploadEventImages)
    public ResponseEntity uploadImageEvent(
            @RequestParam("title") String title,
            @RequestParam("images") List<MultipartFile> images) throws IOException {

        this.resourceService.uploadEventImages(
                this.createUploadRequestDto(title,images));

        return ResponseEntity.ok().build();

    }

    @PostMapping(PodiumEndpoint.uploadEventFiles)
    public ResponseEntity uploadEventFiles(
            @RequestParam("title") String title,
            @RequestParam("files") List<MultipartFile> files) throws IOException {

        System.out.println("Jesten");

        this.resourceService.uploadEventFiles(
                this.createUploadRequestDto(title,files));

        return ResponseEntity.ok().build();

    }

    private ResourceRequestDto createUploadRequestDto(String id, List<MultipartFile> images ){


        ResourceRequestDto requestDto = new ResourceRequestDto();
        requestDto.setId(id);
        requestDto.setFiles(images);

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        return requestDto;
    }

}
