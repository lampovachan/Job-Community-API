package com.tkachuk.jobnetwork.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tkachuk.jobnetwork.message.request.ExperienceRequest;
import com.tkachuk.jobnetwork.message.request.UserRequest;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Experience;
import com.tkachuk.jobnetwork.model.Photo;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.ExperienceRepository;
import com.tkachuk.jobnetwork.repository.UserRepository;
import com.tkachuk.jobnetwork.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Value("${localstack.s3.bucketName2}")
    private String bucketName;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    AmazonS3 configure;

    @PostMapping("/data")
    public ResponseEntity<String> addUserData(@Valid @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName).get();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        String cvName = UUID.randomUUID().toString();
        user.setCvUrl("test" + "/" + cvName);
        userRepository.save(user);

        KafkaService kafkaService = new KafkaService();
        kafkaService.sendMessage(kafkaTemplate, user);

        return ResponseEntity.ok().body("User Data added successfully!");
    }

    @PutMapping("/data")
    public ResponseEntity<?> updateUserData(@Valid @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = Optional.of(userRepository.findByUsername(currentPrincipalName).get());

        if (user.isPresent()) {
            User user1 = user.get();
            user1.setFirstName(userRequest.getFirstName());
            user1.setLastName(userRequest.getLastName());
            user1.setExperiences(userRequest.getExperienceList());
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/data")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName).get();
        try {
            userRepository.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepository.findByUsername(currentPrincipalName);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/experience")
    public ResponseEntity<?> addExperience(@Valid @RequestBody ExperienceRequest experienceRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userRepository.findByUsername(userName);
        if (!user.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<Company> company = companyRepository.findById(experienceRequest.getCompanyId());
        if (!company.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Experience experience = new Experience(experienceRequest.getStart(), experienceRequest.getEnd(), user.get(), company.get());
        experienceRepository.save(experience);
        List<Experience> companyExperiences = company.get().getExperiences();
        List<Experience> userExperiences = user.get().getExperiences();
        companyExperiences.add(experience);
        userExperiences.add(experience);
        company.get().setExperiences(companyExperiences);
        user.get().setExperiences(userExperiences);
        companyRepository.save(company.get());
        userRepository.save(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String fileUrl = "";
        String  status = null;

            //converting multipart file to file
            File file = convertMultiPartToFile(multipartFile);

            //filename
            String fileName = multipartFile.getOriginalFilename();

            fileUrl = bucketName + "/" + fileName;

            status = uploadFileTos3bucket(fileName, file);

            System.out.println(fileUrl);
            System.out.println(status);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Optional<User> user = userRepository.findByUsername(userName);
            if (!user.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            user.get().setPhotoUrl(fileUrl);
            userRepository.save(user.get());

            file.delete();

        return ResponseEntity.ok().body("Photo uploaded successfully!");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    private String uploadFileTos3bucket(String fileName, File file) {
        if (!configure.doesBucketExist(bucketName)) {
            configure.createBucket(bucketName);
        }
        try {
            configure.putObject(new PutObjectRequest(bucketName, fileName, file));
        }catch(AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }
        return "Uploading Successfull -> ";
    }

}
