package com.tkachuk.jobnetwork.message.response;

import com.tkachuk.jobnetwork.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    private String firstName;
    private String lastName;
    private String cvUrl;
    private String photo;
    private List<ExperienceResponse> experiences;
}
