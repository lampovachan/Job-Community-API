package com.tkachuk.cvgenerator.controller;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.itextpdf.text.DocumentException;
import com.tkachuk.cvgenerator.model.Employee;
import com.tkachuk.cvgenerator.service.impl.PdfGeneratorImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.IOException;

@RestController
@RequestMapping(value = "/cv")
@Log
public class CvController {

    private final String CV_BUCKET = "test";

    @PostMapping("/create")
    public String createCV(@RequestBody Employee employee) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("127.0.0.1:4566", "us-west-2");

        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();
        PdfGeneratorImpl employeeServices = new PdfGeneratorImpl();
        String fileName = null;
        try {
            fileName = employeeServices.createEmployeeCv(employee, s3);
        } catch (IOException | com.lowagie.text.DocumentException | DocumentException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> getDocument(@PathVariable String id) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("127.0.0.1:4566", "us-west-2");

        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();

        String fileName = id + ".pdf";
        S3Object object = s3.getObject(CV_BUCKET, fileName);
        S3ObjectInputStream s3is = object.getObjectContent();

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .body(new InputStreamResource(s3is));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<InputStreamResource> updateDocument (@PathVariable String id, @RequestBody Employee employee) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("127.0.0.1:4566", "us-west-2");

        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();

        String fileName = id + ".pdf";
        PdfGeneratorImpl employeeServices = new PdfGeneratorImpl();
        try {
           employeeServices.updateEmployeeCV(employee, s3, fileName);
        } catch (IOException | com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }

        S3Object object = s3.getObject(CV_BUCKET, fileName);
        S3ObjectInputStream s3is = object.getObjectContent();
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .body(new InputStreamResource(s3is));
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable String id) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("127.0.0.1:4566", "us-west-2");

        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();
        String fileName = id + ".pdf";
        s3.deleteObject(CV_BUCKET, fileName);
        return "Deleted Successfully";
    }


}
