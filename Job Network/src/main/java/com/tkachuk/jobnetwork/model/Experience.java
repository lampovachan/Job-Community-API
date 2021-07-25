package com.tkachuk.jobnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "experience")
@Data
public class Experience implements Serializable {

    @Column(name = "start_date")
    private Date start;

    @Column(name = "end_date")
    private Date end;

    @EmbeddedId
    private ExperiencePK id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("company_id")
    private Company company;

    public Experience(Date start, Date end, User user, Company company) {
        this.start = start;
        this.end = end;
        this.user = user;
        this.company = company;
    }

    public Experience() {
    }
}
