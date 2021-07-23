package com.tkachuk.jobnetwork.model;

import javax.persistence.*;

/**
 * Simple object that represents company photo.
 *
 * @author Svitlana Tkachuk
 */

@Table(name = "photos")
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    public Photo(String fileName, Company company) {
        this.url = fileName;
        this.company = company;
    }

    public Photo() {

    }
}
