package com.tkachuk.jobnetwork.service;

import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Experience;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExperienceService {
    @Autowired
    ExperienceRepository experienceRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public void addExperience(ExperienceDto experienceRequest) {
        Optional<User> user = userDetailsService.check();
        Optional<Company> company = companyRepository.findById(experienceRequest.getCompanyId());
        Experience experience = new Experience(experienceRequest.getStart(), experienceRequest.getEnd(), user.get(), company.get());
        experienceRepository.save(experience);
    }
}
