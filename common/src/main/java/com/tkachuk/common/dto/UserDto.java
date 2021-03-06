package com.tkachuk.common.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * Dto class for user requests.
 *
 * @author Svitlana Tkachuk
 */

public class UserDto {
    private String firstName;

    private String lastName;

    @ApiModelProperty(required = false, hidden = true)
    private List<ExperienceDto> experiences;

    @ApiModelProperty(required = false, hidden = true)
    private String photo;

    @ApiModelProperty(required = false, hidden = true)
    private String cvUrl;
    @ApiModelProperty(hidden = true)
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
