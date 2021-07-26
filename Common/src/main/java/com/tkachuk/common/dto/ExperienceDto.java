package com.tkachuk.common.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Dto class for experience requests.
 *
 * @author Svitlana Tkachuk
 */

public class ExperienceDto {

    public ExperienceDto(Long start, Long end, Long companyId) {
        this.start = start;
        this.end = end;
        this.companyId = companyId;
    }

    private Long start;

    private Long end;

    private CompanyDto company;

    private Long companyId;

    public Date getStart() {
        Timestamp timestamp = new Timestamp(start);
        return new Date(timestamp.getTime());
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Date getEnd() {
        Timestamp timestamp = new Timestamp(end);
        return new Date(timestamp.getTime());
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}