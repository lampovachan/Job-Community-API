package com.tkachuk.cvgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Just a POJO representing employee entity.
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private String firstName;
    private String lastName;
    private Integer age;
    private String goal;
    private String experiences;
}
