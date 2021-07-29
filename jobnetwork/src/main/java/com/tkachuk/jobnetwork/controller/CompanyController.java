package com.tkachuk.jobnetwork.controller;

import com.tkachuk.jobnetwork.exception.ResourceNotFoundException;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.service.CompanyService;
import com.tkachuk.jobnetwork.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * Rest controller for company requests.
 *
 * @author Svitlana Tkachuk
 */

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final PhotoService photoService;

    @Autowired
    public CompanyController(CompanyService companyService, PhotoService photoService) {
        this.companyService = companyService;
        this.photoService = photoService;
    }

    @PostMapping("/")
    public ResponseEntity<String> addCompany(@Valid @RequestBody Company companyRequest) {
        companyService.saveCompanyData(companyRequest);
        return ResponseEntity.ok().body("Company added successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @Valid @RequestBody Company companyRequest) {
        if (companyService.updateCompanyById(id, companyRequest) != null) {
            return ResponseEntity.ok().body("Company updated!");
        }
        else {
            throw new ResourceNotFoundException("There is no company with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompanyData(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id) {
        Optional<Company> company = companyService.findCompanyById(id);
        if (company.isPresent()) {
            return new ResponseEntity<>(company.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("There is no company with id " + id);
        }
    }

    @GetMapping("/")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping("/{id}/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable Long id) throws IOException {
        return photoService.uploadFileOfCompany(multipartFile, id);
    }
}
