package com.podium.controller;

import com.podium.configuration.JwtTokenUtil;
import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.controller.dto.response.ExpirationDateTokenControllerResponse;
import com.podium.controller.dto.response.JwtControllerResponse;
import com.podium.controller.dto.response.UsernameFromTokenControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class JwtAuthenticationController {

    private  JwtTokenUtil jwtTokenUtil;
    private  JwtUserDetailsService userDetailsService;
    private  PasswordEncoder passwordEncoder;

    public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(PodiumEndpoint.authenticate)
    public ResponseEntity<JwtControllerResponse> createAuthenticationToken(@RequestBody @PodiumValidBody JwtControllerRequest jwtControllerRequest){

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtControllerRequest.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                jwtControllerRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok(new JwtControllerResponse(jwtTokenUtil.generateToken(userDetails)));

        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

}
