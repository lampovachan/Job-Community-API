package com.tkachuk.common;

import java.util.List;

public class User {

    private String firstName;

    private String lastName;

    private String cvUrl;

    private String photo;

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getCvUrl() {
        return cvUrl;
    }

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

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }
    private List<Experience> experiences;


    public User(String firstName, String lastName, List<Experience> experienceList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experiences = experienceList;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
