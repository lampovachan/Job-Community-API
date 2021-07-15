package com.tkachuk.cvgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExperienceDto implements Serializable {
    private String from;
    private String to;
    private String company;
    private String jobTitle;
    private String about;
}
