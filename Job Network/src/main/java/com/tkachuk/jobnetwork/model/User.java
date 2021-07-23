package com.tkachuk.jobnetwork.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * Simple object that represents user.
 *
 * @author Svitlana Tkachuk
 */

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "cv_url")
    private String cvUrl;

    @Column(name = "photo_url")
    private String photoUrl;

    public User(String username, String email, String encode) {
        this.username = username;
        this.email = email;
        this.password = encode;
    }

    public User() {

    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    public User(String firstName, String lastName, List<Experience> experienceList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experiences = experienceList;
    }
}
