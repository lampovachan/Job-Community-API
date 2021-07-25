package com.tkachuk.common.dto;

import java.util.List;

public class CompanyDto {
    private String name;

    private List<Experience> experienceDtos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Experience> getExperiences() {
        return experienceDtos;
    }

    public void setExperiences(List<Experience> experienceDtos) {
        this.experienceDtos = experienceDtos;
    }
}

