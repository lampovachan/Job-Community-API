package com.tkachuk.common.dto;

import java.util.Date;

/**
 * Dto class for experience requests.
 *
 * @author Svitlana Tkachuk
 */

public class ExperienceDto {

    private Date start;

    private Date end;

    private UserDto userDto;

    private CompanyDto companyDto;

    private Long companyId;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public UserDto getUser() {
        return userDto;
    }

    public void setUser(UserDto userDto) {
        this.userDto = userDto;
    }

    public CompanyDto getCompany() {
        return companyDto;
    }

    public void setCompany(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
