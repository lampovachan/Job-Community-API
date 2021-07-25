package com.tkachuk.jobnetwork.message.request;

import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class ExperienceRequest {

    @Autowired
    UserRepository userRepository;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getUserId() {
        return userId;
    }

    public User getUser() throws Exception {
        Optional <User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    private Date start;

    private Date end;

    private Long userId;

    private Company company;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    private Long companyId;
}
