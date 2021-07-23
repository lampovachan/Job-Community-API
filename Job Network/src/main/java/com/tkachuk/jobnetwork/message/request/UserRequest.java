package com.tkachuk.jobnetwork.message.request;

import com.tkachuk.jobnetwork.model.Experience;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRequest {
    @NotBlank
    @Size(min=3, max=60)
    private String firstName;
    @NotBlank
    @Size(min=3, max=60)
    private String lastName;
    private List<Experience> experienceList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }
}
