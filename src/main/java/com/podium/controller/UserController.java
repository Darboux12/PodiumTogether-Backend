package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.other.FileControllerDto;
import com.podium.controller.dto.request.ProfileUpdateControllerRequest;
import com.podium.controller.dto.request.SignUpControllerRequest;
import com.podium.controller.dto.response.UserControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.User;
import com.podium.service.UserService;
import com.podium.service.dto.request.SignUpServiceDto;
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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(PodiumEndpoint.addUser)
    public ResponseEntity addUser(@RequestBody @PodiumValidBody SignUpControllerRequest request) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {
        this.userService.addUser(this.convertAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("User successfully signed up");
    }

    @GetMapping(PodiumEndpoint.findUserByUsername)
    public ResponseEntity<UserControllerResponse> findUser(@PathVariable String username) throws PodiumEntityNotFoundException {

        var user = this.userService.findUserByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(this.convertEntityToResponseDto(user));
    }

    @GetMapping(PodiumEndpoint.findAllUsers)
    public ResponseEntity<Iterable<UserControllerResponse>> findAllUsers(){

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
    public ResponseEntity updateUserProfile(@RequestBody ProfileUpdateControllerRequest request) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {
        this.userService.updateUser(request);
        return ResponseEntity.ok().build();
    }

    private SignUpServiceDto convertAddRequestToServiceDto(SignUpControllerRequest request){
        return new SignUpServiceDto(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getCountry(),
                request.getBirthday()
        );
    }

    private UserControllerResponse convertEntityToResponseDto(User user) {

        var roles = new HashSet<String>();

        user
                .getRoles()
                .forEach(role -> roles.add(role.getRole()));

        return new UserControllerResponse(
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

    private Iterable<UserControllerResponse> convertEntityIterableToResponseDto(Iterable<User> users){

        var userResponses = new ArrayList<UserControllerResponse>();

        users.forEach(x -> {
            userResponses.add(this.convertEntityToResponseDto(x));
        });

        return userResponses;
    }

    private FileControllerDto findUserProfileImage(User user) {

        PodiumResource userResource = user.getProfileImage();

        if(userResource != null) {

            try {
                return new FileControllerDto(
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
