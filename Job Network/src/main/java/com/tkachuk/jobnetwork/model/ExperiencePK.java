package com.tkachuk.jobnetwork.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
@Data
public class ExperiencePK implements Serializable {

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "company_id")
    private Long company_id;
}
