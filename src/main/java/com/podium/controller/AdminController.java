package com.podium.controller;

import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class AdminController {

    private ResourceService resourceService;

    public AdminController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resources/synchronize")
    public ResponseEntity synchronizeResources(){

        this.resourceService.synchronizeResourcesWithSystemFiles();

        return ResponseEntity.ok().build();

    }




}
