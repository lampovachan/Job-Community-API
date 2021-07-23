package com.tkachuk.jobnetwork.message.request;

import com.tkachuk.jobnetwork.model.Experience;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
public class CompanyRequest {
    public CompanyRequest(){}

    @NotBlank
    @Size(min = 3, max = 60)
    private String name;

    private List<ExperienceRequest> experiences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExperienceRequest> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceRequest> experiences) {
        this.experiences = experiences;
    }
}
