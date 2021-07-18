package com.tkachuk.cvgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Inner entity for employee representing its experience.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Experience {
    private String from;
    private String to;
    private String company;
    private String jobTitle;
    private String about;
}
