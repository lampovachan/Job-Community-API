package com.tkachuk.common.dto;

import java.util.List;

public class UserDto {
    private String firstName;

    private String lastName;

    private List<ExperienceDto> experiences;

    private String photo;

    private String cvUrl;

    private String photoUrl;

    public UserDto(String firstName, String lastName, List<ExperienceDto> experienceDtoList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experiences = experienceDtoList;
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

    public List<ExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceDto> experienceDtos) {
        this.experiences = experienceDtos;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
