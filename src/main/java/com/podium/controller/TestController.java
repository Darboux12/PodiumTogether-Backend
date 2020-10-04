package com.podium.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity getTest(){

        System.out.println("Get  Mapping Succes");

        HttpHeaders headers = new HttpHeaders();
        headers.set("dariusz","bula");

        return ResponseEntity.status(409).body("Err");



    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/test")
    public void postTest(){

        System.out.println("Post  Mapping Succes");

    }


}
