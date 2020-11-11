package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.ProfileUpdateRequestDto;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.service.CountryService;
import com.podium.service.UserService;
import com.podium.validation.main.PodiumValidator;
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

    public UserController(UserService userService, CountryService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }

    @PostMapping(PodiumEndpoint.addUser)
    public ResponseEntity addUser(@RequestBody SignUpRequestDto request){

        PodiumValidator.getInstance().validateRequestBody(request);

        if(this.userService.existUserByUsername(request.getUsername()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with given username already exists");

        if(this.userService.existUserByEmail(request.getEmail()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with given email already exists");

        if(!this.countryService.existCountryByName(request.getCountry()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given country does not exist");

        this.userService.addUser(request);
        return ResponseEntity.ok().body("User successfully signed up");

    }

    @GetMapping(PodiumEndpoint.findUserByUsername)
    public ResponseEntity<UserResponseDto> findUser(@PathVariable String username){

        if(this.userService.existUserByUsername(username))
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.userService.findUserByUsername(username));

        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");

    }

    @GetMapping(PodiumEndpoint.findAllUsers)
    public ResponseEntity<Iterable<UserResponseDto>> findAllUsers(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.findAllUsers());

    }

    @DeleteMapping(PodiumEndpoint.deleteUser)
    public ResponseEntity deleteUser(@PathVariable String username){

        if(this.userService.existUserByUsername(username)){
            this.userService.deleteUserByUsername(username);
            return ResponseEntity.ok().body("User successfully deleted");
        }

        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");

    }

    @GetMapping(PodiumEndpoint.existUserByUsername)
    public ResponseEntity existUserByUsername(@PathVariable String username){

        if(this.userService.existUserByUsername(username))
            return ResponseEntity.ok().build();

        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User not found");

    }

    @GetMapping(PodiumEndpoint.existUserByEmail)
    public ResponseEntity existUserByEmail(@PathVariable String email){

        if(this.userService.existUserByEmail(email))
            return ResponseEntity.ok().build();

        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Email not found");

    }

    @PostMapping(PodiumEndpoint.updateUserProfile)
    public ResponseEntity updateUserProfile(@RequestBody ProfileUpdateRequestDto request){

        PodiumValidator
                .getInstance()
                .validateRequestBody(request);

        if(!this.userService.isUpdateDataConsistent(request))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with given username or email already exist");

        if(!this.countryService.existCountryByName(request.getCountry()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given country does not exist");

        this.userService.updateUser(request);

        return ResponseEntity.ok().build();

    }

}
