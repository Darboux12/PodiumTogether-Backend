package com.podium.controller;

import com.podium.model.SignUpRequest;
import com.podium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody SignUpRequest request){

        this.userService.addUser(request);






        return ResponseEntity.ok().body("User successfully signed up");
    }
}
