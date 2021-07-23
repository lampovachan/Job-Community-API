package com.tkachuk.jobnetwork.service;

import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.dto.request.LoginForm;
import com.tkachuk.jobnetwork.dto.request.SignUpForm;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.UserRepository;
import com.tkachuk.jobnetwork.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private Authentication auth(LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    public String generateJwt(LoginForm loginRequest) {
        Authentication authentication = auth(loginRequest);
        return jwtProvider.generateJwtToken(authentication);
    }

    public UserDetails createUserDetails(LoginForm loginRequest) {
        Authentication authentication = auth(loginRequest);
        return (UserDetails) authentication.getPrincipal();
    }

    public void saveUserData(SignUpForm signUpRequest) {
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
    }

    public User checkAuth(UserDto userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName).get();
    }
}
