package com.tkachuk.jobnetwork.controller;

import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.kafka.KafkaService;
import com.tkachuk.jobnetwork.service.ExperienceService;
import com.tkachuk.jobnetwork.service.PhotoService;
import com.tkachuk.jobnetwork.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;
    private final KafkaService kafkaService;
    private final ExperienceService experienceService;
    private final PhotoService photoService;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService, KafkaService kafkaService, ExperienceService experienceService, PhotoService photoService) {
        this.userDetailsService = userDetailsService;
        this.kafkaService = kafkaService;
        this.experienceService = experienceService;
        this.photoService = photoService;
    }

    @PostMapping("/data")
    public ResponseEntity<String> addUserData(@Valid @RequestBody UserDto userRequest) {
        User user = userDetailsService.addData(userRequest);
        userDetailsService.saveUser(user);
        kafkaService.sendMessageWrapper(user);
        return ResponseEntity.ok().body("User Data added successfully!");
    }

    @PutMapping("/data")
    public ResponseEntity<?> updateUserData(@Valid @RequestBody UserDto userRequest) {
        if (userDetailsService.updateData(userRequest) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/data")
    public ResponseEntity<?> deleteUser() {
        try {
            userDetailsService.deleteUser();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getUserData() {
        Optional<User> user = userDetailsService.check();
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/experience")
    public ResponseEntity<?> addExperience(@Valid @RequestBody ExperienceDto experienceRequest) {
        experienceService.addExperience(experienceRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
       return photoService.uploadFileOfUser(multipartFile);
    }
}
