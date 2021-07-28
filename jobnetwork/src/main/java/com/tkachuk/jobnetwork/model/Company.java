package com.tkachuk.jobnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Simple object that represents company.
 *
 * @author Svitlana Tkachuk
 */

@Entity
@Table(name = "companies")
@Data
public class Company {
    @Id
    @ApiModelProperty(required = false, hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private List<Photo> photos;

    public Company() {}

    public Company(String name) {
        this.name = name;
    }
    public Company(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
