package com.podium.controller;

import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.service.CountryService;
import com.podium.service.UserService;
import com.podium.validation.PodiumValidator;
import com.podium.validation.validators.PodiumValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

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
    public ResponseEntity addUser(@RequestBody SignUpRequestDto request) throws ParseException, IllegalAccessException {

        PodiumValidator.getInstance().validateRequestBody(request);

        if(this.userService.existUserByUsername(request.getUsername()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with given username already exists");

        if(this.userService.existUserByEmail(request.getEmail()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with given email already exists");





         /*

        if(this.userService.existUserByEmail(request.getEmail()))
            return PodiumValidationResponse.AlreadyExist("Email");

        if(PodiumValidator.isTextEmpty(request.getUsername()))
            return PodiumValidationResponse.EmptyValue("Username");

        if(PodiumValidator.isLongerThan(request.getUsername(),
                PodiumLimits.maxUsernameLength))
            return PodiumValidationResponse
                    .TooLong("Username",PodiumLimits.maxUsernameLength);

        if(PodiumValidator.isShorterThan(request.getUsername(),
                PodiumLimits.minUsernameLength))
            return PodiumValidationResponse
                    .TooShort("Username",PodiumLimits.minUsernameLength);

        if(PodiumValidator.isLongerThan(request.getPassword(),
                PodiumLimits.maxPasswordLength))
            return PodiumValidationResponse
                    .TooLong("Password",PodiumLimits.maxPasswordLength);

        if(PodiumValidator.isShorterThan(request.getPassword(),
                PodiumLimits.minPasswordLength))
            return PodiumValidationResponse
                    .TooShort("Password",PodiumLimits.minPasswordLength);

        if(PodiumValidator.isTextEmpty(request.getEmail()))
            return PodiumValidationResponse.EmptyValue("Email");

        if(PodiumValidator.isTextEmpty(request.getPassword()))
            return PodiumValidationResponse.EmptyValue("Password");

        if(PodiumValidator.isNull(request.getBirthday()))
            return PodiumValidationResponse.EmptyValue("Birthday");

        if(!countryService.existCountryByName(request.getCountry()))
            return PodiumValidationResponse.NonexistentValue("Country");*/

        this.userService.addUser(request);
        return ResponseEntity.ok().body("User successfully signed up");

    }

    @GetMapping("/user/find/{username}")
    public ResponseEntity findUser(@PathVariable String username){

        if(this.userService.existUserByUsername(username))
            return ResponseEntity
                    .status(200)
                    .body(this.userService.findUserByUsername(username));

        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");

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

        else
            return PodiumValidationResponse.NotFoundValue("Username");

    }

    @GetMapping("/user/exist/username/{username}")
    public ResponseEntity existUserByUsername(@PathVariable String username){

        if(!this.userService.existUserByUsername(username))
            return ResponseEntity.ok().build();

        else
            return PodiumValidationResponse.NonexistentValue("Username");

    }

    @GetMapping("/user/exist/email/{email}")
    public ResponseEntity existUserByEmail(@PathVariable String email){

        if(!this.userService.existUserByEmail(email))
            return ResponseEntity.ok().build();

        else
            return PodiumValidationResponse.NonexistentValue("Email");

    }

}
