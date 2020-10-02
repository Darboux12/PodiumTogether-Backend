package com.podium.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/test")
    public void getTest(){

        System.out.println("Get  Mapping Succes");



    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/test")
    public void postTest(){

        System.out.println("Post  Mapping Succes");

    }


}
