package com.podium.controller.authentication;

import com.podium.configuration.JwtTokenUtil;
import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.authentication.JwtRequestDto;
import com.podium.model.dto.response.authentication.ExpirationDateTokenResponse;
import com.podium.model.dto.response.authentication.JwtResponseDto;
import com.podium.model.dto.response.authentication.UsernameFromTokenResponseDto;
import com.podium.service.authentication.JwtUserDetailsService;
import com.podium.service.user.UserService;
import com.podium.validation.validators.PodiumValidator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

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

    @PostMapping(PodiumEndpoint.authenticate)
    public ResponseEntity<JwtResponseDto> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {

        PodiumValidator.getInstance().validateRequestBody(authenticationRequest);

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                authenticationRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok(new JwtResponseDto(jwtTokenUtil.generateToken(userDetails)));

        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    @PostMapping(PodiumEndpoint.authenticateNoToken)
    public ResponseEntity<JwtResponseDto> authenticateWithoutToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {

        PodiumValidator.getInstance().validateRequestBody(authenticationRequest);

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final boolean accessPermission = this.passwordEncoder.matches(
                authenticationRequest.getPassword(),
                userDetails.getPassword());

        if(accessPermission)
            return ResponseEntity.ok().build();

        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    @PostMapping(PodiumEndpoint.findUsernameFromToken)
    public ResponseEntity<UsernameFromTokenResponseDto> findUsernameFromToken(@RequestParam("token") String token){

        try{
                String username = this.jwtTokenUtil.getUsernameFromToken(token);

                UsernameFromTokenResponseDto response
                        = new UsernameFromTokenResponseDto();

                response.setUsername(username);

                return ResponseEntity
                        .ok()
                        .body(response);

        }
        catch (ExpiredJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
        }

        catch (MalformedJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token in invalid");
        }


    }

    @PostMapping(PodiumEndpoint.findTokenExpirationDate)
    public ResponseEntity<ExpirationDateTokenResponse> findExpirationFromToken(@RequestParam("token") String token){

        try{

            ExpirationDateTokenResponse response =
                    new ExpirationDateTokenResponse();

            Date expirationDate =
                    this.jwtTokenUtil.getExpirationDateFromToken(token);

            response.setExpirationDate(expirationDate);

            return ResponseEntity
                    .ok()
                    .body(response);

        }
        catch (ExpiredJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
        }

        catch (MalformedJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token in invalid");
        }


    }

}