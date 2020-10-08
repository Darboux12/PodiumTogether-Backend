package com.podium.controller;

import com.podium.model.entity.User;
import com.podium.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/role")
    public Iterable<User> getRole(){

        return this.userService.findAllByRole("subscriber");







    }

}
