package com.tkachuk.common.dto;

/**
 * Dto class for company requests.
 *
 * @author Svitlana Tkachuk
 */

public class CompanyDto {
    private Long id;
    private String name;

    public CompanyDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}

