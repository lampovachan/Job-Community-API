package com.tkachuk.jobnetwork.controller;

import com.tkachuk.jobnetwork.message.request.LoginForm;
import com.tkachuk.jobnetwork.message.request.SignUpForm;
import com.tkachuk.jobnetwork.message.response.JwtResponse;
import com.tkachuk.jobnetwork.repository.UserRepository;
import com.tkachuk.jobnetwork.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        String jwt = authService.generateJwt(loginRequest);
        UserDetails userDetails = authService.createUserDetails(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

       authService.saveUserData(signUpRequest);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}

