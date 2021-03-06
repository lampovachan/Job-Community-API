package com.tkachuk.jobnetwork.controller;

import com.tkachuk.jobnetwork.dto.request.LoginForm;
import com.tkachuk.jobnetwork.dto.request.SignUpForm;
import com.tkachuk.jobnetwork.dto.response.JwtResponse;
import com.tkachuk.jobnetwork.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller for registration and authentication.
 *
 * @author Svitlana Tkachuk
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        String jwt = authService.generateJwt(loginRequest);
        UserDetails userDetails = authService.createUserDetails(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    @ApiOperation("sign up request")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(authService.existsByUsername(signUpRequest)) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(authService.existsByEmail(signUpRequest)) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

       authService.saveUserData(signUpRequest);
        return ResponseEntity.ok().body("User registered successfully!");
    }
}

