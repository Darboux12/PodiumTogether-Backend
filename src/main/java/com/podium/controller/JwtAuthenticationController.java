package com.podium.controller;

import com.podium.configuration.JwtTokenUtil;
import com.podium.model.dto.request.JwtRequestDto;
import com.podium.model.dto.response.JwtResponseDto;
import com.podium.service.JwtUserDetailsService;
import com.podium.service.UserService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDto> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {

        PodiumValidator.getInstance().validateRequestBody(authenticationRequest);

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                authenticationRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok(new JwtResponseDto(jwtTokenUtil.generateToken(userDetails),authenticationRequest.getUsername()));

        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Authentication failed");
    }

}