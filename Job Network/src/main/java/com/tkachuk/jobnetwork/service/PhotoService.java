package com.tkachuk.jobnetwork.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Photo;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.PhotoRepository;
import com.tkachuk.jobnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Service class for photo requests.
 *
 * @author Svitlana Tkachuk
 */

@Service
public class PhotoService {
    @Value("${localstack.s3.bucketName}")
    private String bucketName;

    @Value("${localstack.s3.bucketName2}")
    private String bucketName2;

    private final AmazonS3 configure;
    private final PhotoRepository photoRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public PhotoService(AmazonS3 configure, PhotoRepository photoRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.configure = configure;
        this.photoRepository = photoRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
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
        } catch (AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }
        return "Uploading Successfull -> ";
    }

    private File getFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        return file;
    }
    private String getFilename(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        return fileName;
    }

    private String uploadWrapper(MultipartFile multipartFile) throws IOException {
        File file = getFile(multipartFile);
        String fileName = getFilename(multipartFile);
        String status = uploadFileTos3bucket(fileName, file);
        return status;
    }

    private Company saveCompanyPhoto(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        Company company1 = new Company();

        if (company.isPresent()) {
            company1 = company.get();
        }
        return company1;
    }

    private String saveUserPhoto(MultipartFile multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userRepository.findByUsername(userName);
        String fileName = getFilename(multipartFile);
        String fileUrl = "";
        fileUrl = bucketName2 + "/" + fileName;
        user.get().setPhotoUrl(fileUrl);
        userRepository.save(user.get());
        return fileUrl;
    }

    public String uploadFileOfCompany(MultipartFile multipartFile, Long id) throws IOException {
        String fileUrl = "";
        File file = getFile(multipartFile);
        String fileName = getFilename(multipartFile);
        String status = uploadWrapper(multipartFile);
        fileUrl = bucketName + "/" + fileName;
        Company company1 = saveCompanyPhoto(id);
        Photo photo = new Photo(fileUrl, company1);
        photoRepository.save(photo);
        file.delete();
        return status + " " +  fileUrl;
    }

    public String uploadFileOfUser(MultipartFile multipartFile) throws IOException {
        File file = getFile(multipartFile);
        String status = uploadWrapper(multipartFile);
        String fileUrl = saveUserPhoto(multipartFile);

        file.delete();
        return status + " " + fileUrl;
    }
}
