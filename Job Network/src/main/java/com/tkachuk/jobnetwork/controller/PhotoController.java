package com.tkachuk.jobnetwork.controller;

import com.tkachuk.jobnetwork.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/companies")
public class PhotoController {
   private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/{id}/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable Long id) throws IOException {
        return photoService.uploadFileOfCompany(multipartFile, id);
    }

}

