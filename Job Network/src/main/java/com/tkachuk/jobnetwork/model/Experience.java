package com.tkachuk.jobnetwork.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "experience")
@Data
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Date start;

    @Column(name = "end_date")
    private Date end;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
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
