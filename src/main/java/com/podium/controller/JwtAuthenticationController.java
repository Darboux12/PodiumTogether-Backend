package com.podium.controller;

import com.podium.configuration.JwtTokenUtil;
import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.JwtRequest;
import com.podium.controller.dto.response.ExpirationDateTokenResponse;
import com.podium.controller.dto.response.JwtResponse;
import com.podium.controller.dto.response.UsernameFromTokenResponse;
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
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody @PodiumValidBody JwtRequest jwtRequest){

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                jwtRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));

        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    @PostMapping(PodiumEndpoint.authenticateNoToken)
    public ResponseEntity<JwtResponse> authenticateWithoutToken(@RequestBody @PodiumValidBody JwtRequest jwtRequest){

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                jwtRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok().build();

        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    @PostMapping(PodiumEndpoint.findUsernameFromToken)
    public ResponseEntity<UsernameFromTokenResponse> findUsernameFromToken(@RequestParam("token") @PodiumValidVariable String token){

        try{
                String username = this.jwtTokenUtil.getUsernameFromToken(token);
                UsernameFromTokenResponse response = new UsernameFromTokenResponse();
                response.setUsername(username);
                return ResponseEntity.ok().body(response);
        }
        catch (ExpiredJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
        }
        catch (MalformedJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token in invalid");
        }

    }

    @PostMapping(PodiumEndpoint.findTokenExpirationDate)
    public ResponseEntity<ExpirationDateTokenResponse> findExpirationFromToken(@RequestParam("token") @PodiumValidVariable String token){

        try{

            ExpirationDateTokenResponse response = new ExpirationDateTokenResponse();
            Date expirationDate = this.jwtTokenUtil.getExpirationDateFromToken(token);
            response.setExpirationDate(expirationDate);

            return ResponseEntity.ok().body(response);
        }
        catch (ExpiredJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
        }
        catch (MalformedJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token in invalid");
        }

    }

}