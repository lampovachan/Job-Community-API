package com.tkachuk.jobnetwork.service;

import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.Experience;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.repository.ExperienceRepository;
import com.tkachuk.jobnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    CompanyRepository companyRepository;

    public User addData(UserDto userRequest) {
        User user = authService.checkAuth(userRequest);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        String cvName = UUID.randomUUID().toString();
        user.setCvUrl("test" + "/" + cvName);
        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return Optional.of(userRepository.findByUsername(currentPrincipalName).get());
    }

    public User updateData(UserDto userRequest) {
        Optional<User> user = check();
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setFirstName(userRequest.getFirstName());
            user1.setLastName(userRequest.getLastName());
            return userRepository.save(user1);
        }
        return null;
    }

    private User findUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName).get();
    }

    public void deleteUser() {
        User user = findUserByUsername();
        userRepository.delete(user);
    }

    public void addExperience(ExperienceDto experienceRequest) {
        Optional<User> user = check();
        Optional<Company> company = companyRepository.findById(experienceRequest.getCompanyId());
        Experience experience = new Experience(experienceRequest.getStart(), experienceRequest.getEnd(), user.get(), company.get());
        experienceRepository.save(experience);
    }
}
