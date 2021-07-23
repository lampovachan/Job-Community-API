package com.tkachuk.common.dto;

import java.util.List;

/**
 * Dto class for user requests.
 *
 * @author Svitlana Tkachuk
 */

public class UserDto {

    private String firstName;

    private String lastName;

    private List<ExperienceDto> experienceDtos;

    private String photo;

    public UserDto(String firstName, String lastName, List<ExperienceDto> experienceDtoList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experienceDtos = experienceDtoList;
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
        return experienceDtos;
    }

    public void setExperiences(List<ExperienceDto> experienceDtos) {
        this.experienceDtos = experienceDtos;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
