package com.tkachuk.jobnetwork.controller;

import com.tkachuk.jobnetwork.message.request.CompanyRequest;
import com.tkachuk.jobnetwork.message.request.ExperienceRequest;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Experience;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.ExperienceRepository;
import com.tkachuk.jobnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity<String> addCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        Company company = new Company(companyRequest.getName());
        companyRepository.save(company);
        return ResponseEntity.ok().body("Company added successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyRequest companyRequest) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isPresent()) {
            Company company1 = company.get();
            company1.setName(companyRequest.getName());
            return new ResponseEntity<>(companyRepository.save(company1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        try {
            companyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isPresent()) {
            return new ResponseEntity<>(company.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
