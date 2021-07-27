package com.tkachuk.jobnetwork.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.kafka.KafkaService;
import com.tkachuk.jobnetwork.service.PhotoService;
import com.tkachuk.jobnetwork.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

/**
 * Rest controller for user requests.
 *
 * @author Svitlana Tkachuk
 */

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final KafkaService kafkaService;
    private final PhotoService photoService;

    @Autowired
    public UserController(UserService userService, KafkaService kafkaService, PhotoService photoService) {
        this.userService = userService;
        this.kafkaService = kafkaService;
        this.photoService = photoService;
    }

    @PostMapping("/data")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<String> addUserData(@Valid @RequestBody UserDto userRequest) {
        User user = userService.addData(userRequest);
        userService.saveUser(user);
        kafkaService.sendMessageWrapper(user);
        return ResponseEntity.ok().body("User Data added successfully!");
    }

    @PutMapping("/data")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> updateUserData(@Valid @RequestBody UserDto userRequest) {
        if (userService.updateData(userRequest) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/data")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> deleteUser() {
        try {
            userService.deleteUser();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getUserData() {
        Optional<User> user = userService.check();
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cv")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getUserCv() {
        Optional<User> user = userService.check();
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        S3Object object = userService.getCvFromS3(user.get().getCvUrl().split("/")[1]);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + user.get().getCvUrl().split("/")[1] + ".pdf")
                .body(new InputStreamResource(object.getObjectContent()));
    }

    @PostMapping("/experience")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> addExperience(@Valid @RequestBody ExperienceDto experienceRequest) {
        userService.addExperience(experienceRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        return photoService.uploadFileOfUser(multipartFile);
    }
}
