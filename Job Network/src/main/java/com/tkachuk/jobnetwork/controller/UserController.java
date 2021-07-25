package com.tkachuk.jobnetwork.controller;

import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.kafka.KafkaService;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.ExperienceRepository;
import com.tkachuk.jobnetwork.service.PhotoService;
import com.tkachuk.jobnetwork.service.UserService;
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
    @Autowired
    UserService userService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ExperienceRepository experienceRepository;
    private final KafkaService kafkaService;
    private final PhotoService photoService;

    @Autowired
    public UserController(UserService userService, KafkaService kafkaService, PhotoService photoService) {
        this.userService = userService;
        this.kafkaService = kafkaService;
        this.photoService = photoService;
    }

    @PostMapping("/data")
    public ResponseEntity<String> addUserData(@Valid @RequestBody UserDto userRequest) {
        User user = userService.addData(userRequest);
        userService.saveUser(user);
        kafkaService.sendMessageWrapper(user);
        return ResponseEntity.ok().body("User Data added successfully!");
    }

    @PutMapping("/data")
    public ResponseEntity<?> updateUserData(@Valid @RequestBody UserDto userRequest) {
        if (userService.updateData(userRequest) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/data")
    public ResponseEntity<?> deleteUser() {
        try {
            userService.deleteUser();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getUserData() {
        Optional<User> user = userService.check();
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/experience")
    public ResponseEntity<?> addExperience(@Valid @RequestBody ExperienceDto experienceRequest) {
        userService.addExperience(experienceRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        return photoService.uploadFileOfUser(multipartFile);
    }
}
