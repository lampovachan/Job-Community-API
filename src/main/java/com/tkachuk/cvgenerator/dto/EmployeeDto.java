package com.tkachuk.cvgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto implements Serializable {
    private String firstName;
    private String lastName;
    private Integer age;
    private String goal;
    private List<ExperienceDto> experiences;
}
