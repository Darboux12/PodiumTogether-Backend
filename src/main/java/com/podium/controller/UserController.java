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
import com.podium.service.dto.request.ProfileUpdateServiceRequest;
import com.podium.service.dto.request.SignUpServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ALL
    @PostMapping(PodiumEndpoint.addUser)
    public ResponseEntity addUser(@RequestBody @PodiumValidBody SignUpControllerRequest request) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {
        this.userService.addUser(this.convertAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("User successfully signed up");
    }

    // ADMIN | MODERATOR | USER
    @GetMapping(PodiumEndpoint.findUserByUsername)
    public ResponseEntity<UserControllerResponse> findUser(@PathVariable String username, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        var user = this.userService.findUserByUsername(username,authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(this.convertEntityToResponseDto(user));
    }

    // ADMIN
    @GetMapping(PodiumEndpoint.findAllUsers)
    public ResponseEntity<Iterable<UserControllerResponse>> findAllUsers(Authentication authentication) throws PodiumAuthorityException, PodiumEntityNotFoundException {
        var users = this.userService.findAllUsers(authentication.getName());
        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(users));
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteUser)
    public ResponseEntity deleteUser(@PathVariable @PodiumValidVariable String username, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
            this.userService.deleteUserByUsername(username,authentication.getName());
            return ResponseEntity.ok().body("User successfully deleted");
    }

    // ALL
    @GetMapping(PodiumEndpoint.existUserByUsername)
    public ResponseEntity existUserByUsername(@PathVariable @PodiumValidVariable String username){
            return ResponseEntity.ok().body(this.userService.existUserByUsername(username));
    }

    @GetMapping(PodiumEndpoint.existUserByEmail)
    public ResponseEntity existUserByEmail(@PathVariable @PodiumValidVariable String email){
            return ResponseEntity.ok().body(this.userService.existUserByEmail(email));
    }

    // USER
    @PostMapping(PodiumEndpoint.updateUserProfile)
    public ResponseEntity updateUserProfile(@RequestPart(name = "request") @PodiumValidBody ProfileUpdateControllerRequest request,
                                            @RequestPart(name = "image",required = false) MultipartFile image, Authentication authentication) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumAuthorityException {
        this.userService.updateUser(this.convertUpdateRequestToServiceRequest(request,image),authentication.getName());
        return ResponseEntity.ok().build();
    }

    private ProfileUpdateServiceRequest convertUpdateRequestToServiceRequest(ProfileUpdateControllerRequest request,MultipartFile image){

        return new ProfileUpdateServiceRequest(
                request.getId(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getCountry(),
                request.getBirthday(),
                request.getDescription(),
                image
        );

    }

    private SignUpServiceRequest convertAddRequestToServiceDto(SignUpControllerRequest request){
        return new SignUpServiceRequest(
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
