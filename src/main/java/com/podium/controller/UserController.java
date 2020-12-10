package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.ProfileUpdateRequestDto;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.service.CountryService;
import com.podium.service.UserService;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(PodiumEndpoint.addUser)
    public ResponseEntity addUser(@RequestBody @PodiumValidBody SignUpRequestDto request){
        this.userService.addUser(request);
        return ResponseEntity.ok().body("User successfully signed up");
    }

    @GetMapping(PodiumEndpoint.findUserByUsername)
    public ResponseEntity<UserResponseDto> findUser(@PathVariable String username){
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.findUserByUsername(username));
    }

    @GetMapping(PodiumEndpoint.findAllUsers)
    public ResponseEntity<Iterable<UserResponseDto>> findAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.findAllUsers());

    }

    @DeleteMapping(PodiumEndpoint.deleteUser)
    public ResponseEntity deleteUser(@PathVariable @PodiumValidVariable String username){
            this.userService.deleteUserByUsername(username);
            return ResponseEntity.ok().body("User successfully deleted");
    }

    @GetMapping(PodiumEndpoint.existUserByUsername)
    public ResponseEntity existUserByUsername(@PathVariable @PodiumValidVariable String username){
            return ResponseEntity.ok().body(this.userService.existUserByUsername(username));
    }

    @GetMapping(PodiumEndpoint.existUserByEmail)
    public ResponseEntity existUserByEmail(@PathVariable @PodiumValidVariable String email){
            return ResponseEntity.ok().body(this.userService.existUserByEmail(email));
    }

    @PostMapping(PodiumEndpoint.updateUserProfile)
    public ResponseEntity updateUserProfile(@RequestBody ProfileUpdateRequestDto request){
        this.userService.updateUser(request);
        return ResponseEntity.ok().build();
    }

}
