package com.podium.controller;

import com.podium.model.SignUpRequest;
import com.podium.model.User;
import com.podium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody SignUpRequest request){

        if(this.userService.existUserByUsername(request.getUsername()))
            return ResponseEntity.status(409).body("User with given username already exist");

        else if(this.userService.existUserByEmail(request.getEmail()))
            return ResponseEntity.status(409).body("User with given email already exist");

        else{
            this.userService.addUser(request);
            return ResponseEntity.ok().body("User successfully signed up");
        }

    }

    @GetMapping("/user/get/{username}")
    public void getUser(@PathVariable String username){



        User user = this.userService.findUserByUsername(username);






    }
}
