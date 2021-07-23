package com.tkachuk.common;

import java.util.List;

public class Company {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    private String name;

    private List<Experience> experiences;

    public Company(String name, List<Experience> experiences) {
        this.name = name;
        this.experiences = experiences;
    }

    public Company() {}
}

