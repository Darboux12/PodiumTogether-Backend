package com.podium.controller;

import com.podium.model.dto.response.UserResponseDto;
import com.podium.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/role/{role}")
    public ResponseEntity<Iterable<UserResponseDto>>
    findAllUsersByRole(@PathVariable String role){

        return ResponseEntity
                .ok().build();

    }

}
