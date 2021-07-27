package com.tkachuk.jobnetwork.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.jobnetwork.dto.request.LoginForm;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Experience;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.ExperienceRepository;
import com.tkachuk.jobnetwork.repository.UserRepository;
import com.tkachuk.jobnetwork.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ExperienceRepository experienceRepository;

    @MockBean
    CompanyRepository companyRepository;

    @Autowired
    AuthService authService;

    User user;
    String jwt;

    @Before
    public void setUp()  {
        this.user = new User("username", "email@gmail.com", "password");
        this.jwt = Jwts.builder()
                .setSubject("username")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3600 * 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void getUserData() throws Exception {
        user.setFirstName("firstName");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/data")
                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("firstName")));    }

    @Test
    public void addUserDataSuccessful() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users/data")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void deleteUserDataSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/data")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}