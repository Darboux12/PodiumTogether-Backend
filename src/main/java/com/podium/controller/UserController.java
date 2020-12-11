package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.ProfileUpdateRequest;
import com.podium.controller.dto.request.SignUpRequest;
import com.podium.controller.dto.response.UserResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.UserService;
import com.podium.service.dto.SignUpServiceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(PodiumEndpoint.addUser)
    public ResponseEntity addUser(@RequestBody @PodiumValidBody SignUpRequest request){
        this.userService.addUser(this.convertAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("User successfully signed up");
    }

    @GetMapping(PodiumEndpoint.findUserByUsername)
    public ResponseEntity<UserResponse> findUser(@PathVariable String username){
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.findUserByUsername(username));
    }

    @GetMapping(PodiumEndpoint.findAllUsers)
    public ResponseEntity<Iterable<UserResponse>> findAllUsers(){
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
    public ResponseEntity updateUserProfile(@RequestBody ProfileUpdateRequest request){
        this.userService.updateUser(request);
        return ResponseEntity.ok().build();
    }

    private SignUpServiceDto convertAddRequestToServiceDto(SignUpRequest request){
        return new SignUpServiceDto(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getCountry(),
                request.getBirthday()
        );
    }


}
