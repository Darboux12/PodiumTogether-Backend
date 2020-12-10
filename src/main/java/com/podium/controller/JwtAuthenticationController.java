package com.podium.controller;

import com.podium.configuration.JwtTokenUtil;
import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.JwtRequestDto;
import com.podium.model.dto.response.ExpirationDateTokenResponse;
import com.podium.model.dto.response.JwtResponseDto;
import com.podium.model.dto.response.UsernameFromTokenResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
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
    public ResponseEntity<JwtResponseDto> createAuthenticationToken(@RequestBody @PodiumValidBody JwtRequestDto jwtRequestDto){

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtRequestDto.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                jwtRequestDto.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok(new JwtResponseDto(jwtTokenUtil.generateToken(userDetails)));

        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    @PostMapping(PodiumEndpoint.authenticateNoToken)
    public ResponseEntity<JwtResponseDto> authenticateWithoutToken(@RequestBody @PodiumValidBody JwtRequestDto jwtRequestDto){

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtRequestDto.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                jwtRequestDto.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok().build();

        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    @PostMapping(PodiumEndpoint.findUsernameFromToken)
    public ResponseEntity<UsernameFromTokenResponseDto> findUsernameFromToken(@RequestParam("token") @PodiumValidVariable String token){

        try{
                String username = this.jwtTokenUtil.getUsernameFromToken(token);
                UsernameFromTokenResponseDto response = new UsernameFromTokenResponseDto();
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