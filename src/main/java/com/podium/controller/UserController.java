package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.other.PodiumFileDto;
import com.podium.controller.dto.request.ProfileUpdateRequest;
import com.podium.controller.dto.request.SignUpRequest;
import com.podium.controller.dto.response.RoleResponse;
import com.podium.controller.dto.response.UserResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.News;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.User;
import com.podium.service.UserService;
import com.podium.service.dto.SignUpServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(PodiumEndpoint.addUser)
    public ResponseEntity addUser(@RequestBody @PodiumValidBody SignUpRequest request) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {
        this.userService.addUser(this.convertAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("User successfully signed up");
    }

    @GetMapping(PodiumEndpoint.findUserByUsername)
    public ResponseEntity<UserResponse> findUser(@PathVariable String username) throws PodiumEntityNotFoundException {

        var user = this.userService.findUserByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(this.convertEntityToResponseDto(user));
    }

    @GetMapping(PodiumEndpoint.findAllUsers)
    public ResponseEntity<Iterable<UserResponse>> findAllUsers(){

        var users = this.userService.findAllUsers();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(users));

    }

    @DeleteMapping(PodiumEndpoint.deleteUser)
    public ResponseEntity deleteUser(@PathVariable @PodiumValidVariable String username) throws PodiumEntityNotFoundException {
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
    public ResponseEntity updateUserProfile(@RequestBody ProfileUpdateRequest request) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {
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

    private UserResponse convertEntityToResponseDto(User user) {

        var roles = new HashSet<String>();

        user
                .getRoles()
                .forEach(role -> roles.add(role.getRole()));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCountry().getName(),
                roles,
                user.getBirthday(),
                this.findUserProfileImage(user),
                user.getDescription()
        );

    }

    private Iterable<UserResponse> convertEntityIterableToResponseDto(Iterable<User> users){

        var userResponses = new ArrayList<UserResponse>();

        users.forEach(x -> {
            userResponses.add(this.convertEntityToResponseDto(x));
        });

        return userResponses;
    }

    private PodiumFileDto findUserProfileImage(User user) {

        PodiumResource userResource = user.getProfileImage();

        if(userResource != null) {

            try {
                return new PodiumFileDto(
                        userResource.getName(),
                        userResource.getType(),
                        FileCopyUtils.copyToByteArray(new File(userResource.getPath()))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

}
