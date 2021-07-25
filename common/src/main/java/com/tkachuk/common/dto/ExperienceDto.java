package com.tkachuk.common.dto;

import java.sql.Timestamp;
import java.util.Date;

public class ExperienceDto {

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