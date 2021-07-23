package com.tkachuk.jobnetwork.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Photo;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class PhotoController {
    @Value("${localstack.s3.bucketName}")
    private String bucketName;
    private final AmazonS3 configure;
    private final PhotoRepository photoRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public PhotoController(AmazonS3 configure, PhotoRepository photoRepository, CompanyRepository companyRepository) {
        this.configure = configure;
        this.photoRepository = photoRepository;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/{id}/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable Long id) {

        String fileUrl = "";
        String  status = null;
        try {

            //converting multipart file to file
            File file = convertMultiPartToFile(multipartFile);
            Company company1 = new Company();

            //filename
            String fileName = multipartFile.getOriginalFilename();

            fileUrl = bucketName + "/" + fileName;

            status = uploadFileTos3bucket(fileName, file);
            Optional<Company> company = companyRepository.findById(id);

            if (company.isPresent()) {
                company1 = company.get();
            }
            Photo photo = new Photo(fileUrl, company1);
            photoRepository.save(photo);

            file.delete();

        } catch (Exception e) {

            return "UploadController().uploadFile().Exception : " + e.getMessage();

        }

        return status + " " +  fileUrl;
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

