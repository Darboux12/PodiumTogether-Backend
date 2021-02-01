package com.podium.controller;

import com.podium.configuration.JwtTokenUtil;
import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.controller.dto.response.JwtControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Ban;
import com.podium.dal.entity.User;
import com.podium.service.JwtUserDetailsService;
import com.podium.service.UserService;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumUserBannedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class JwtAuthenticationController {

    private  JwtTokenUtil jwtTokenUtil;
    private  JwtUserDetailsService userDetailsService;
    private UserService userService;
    private  PasswordEncoder passwordEncoder;

    public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(PodiumEndpoint.authenticate)
    public ResponseEntity<JwtControllerResponse> createAuthenticationToken(@RequestBody @PodiumValidBody JwtControllerRequest jwtControllerRequest) throws PodiumAuthorityException, PodiumEntityNotFoundException, PodiumUserBannedException {

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtControllerRequest.getUsername());

        User user = this.userService.findUserByUsername(jwtControllerRequest.getUsername(),jwtControllerRequest.getUsername());

        LocalDateTime currentDate = LocalDateTime.now();

        for(Ban ban : user.getBans())
            if(currentDate.isBefore(ban.getDateTo()) && currentDate.isAfter(ban.getDateFrom()))
                throw new PodiumUserBannedException(ban.getDateTo(),ban.getReason());

        final boolean accessPermission = this.passwordEncoder.matches(
                jwtControllerRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok(new JwtControllerResponse(jwtTokenUtil.generateToken(userDetails)));

        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

}
