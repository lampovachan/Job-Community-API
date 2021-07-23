package com.tkachuk.jobnetwork.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
public class Company extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Photo> photos;

    public Company() {}

    public Company(String name, List<Experience> experiences) {
        this.name = name;
        this.experiences = experiences;
    }

    public Company(String name) {
        this.name = name;
    }
}
