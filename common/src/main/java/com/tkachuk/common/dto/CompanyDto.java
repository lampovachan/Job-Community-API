package com.tkachuk.common.dto;


import java.util.List;

/**
 * Dto class for company requests.
 */

public class CompanyDto {

    private String name;

    private List<ExperienceDto> experienceDtos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExperienceDto> getExperiences() {
        return experienceDtos;
    }

    public void setExperiences(List<ExperienceDto> experienceDtos) {
        this.experienceDtos = experienceDtos;
    }
}

