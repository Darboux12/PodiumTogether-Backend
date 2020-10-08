package com.podium.controller;

import com.podium.model.request.SignUpRequest;
import com.podium.service.CountryService;
import com.podium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private UserService userService;
    private CountryService countryService;

    @Autowired
    public UserController(UserService userService, CountryService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }

    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody SignUpRequest request){

        if(this.userService.existUserByUsername(request.getUsername())){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Already-Exist","Username");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("User with given username already exist");
        }

        if(this.userService.existUserByEmail(request.getEmail())){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Already-Exist","Email");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("User with given email already exist");
        }

        if(request.getUsername().isEmpty()) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Username");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Username cannot be empty");
        }

        if(request.getUsername().length() > 30) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Length-Error","Username");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Username cannot be longer than 30 signs");
        }

        if(request.getEmail().isEmpty()) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Email");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Email cannot be empty");
        }

        if(request.getPassword().isEmpty()) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Password");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Password cannot be empty");
        }

        if(request.getBirthday() == null) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Birthday");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Birthday cannot be empty");
        }

        if(!countryService.existCountryByName(request.getCountry())) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Impossible-Value","Country");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Value does not exist");
        }

        this.userService.addUser(request);
        return ResponseEntity.ok().body("User successfully signed up");

    }

    @GetMapping("/user/find/{username}")
    public ResponseEntity findUser(@PathVariable String username){

            return ResponseEntity
                    .status(200)
                    .body(this.userService.findUserByUsername(username));

    }

    @GetMapping("/user/find/all")
    public ResponseEntity findAllUsers(){

        return ResponseEntity
                .status(200)
                .body(this.userService.findAllUsers());

    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){

        if(this.userService.existUserByUsername(username)){
            this.userService.deleteUserByUsername(username);
            return ResponseEntity.ok().body("User successfully deleted");
        }

        else return ResponseEntity.status(404).body("User do not exist");

    }
}
