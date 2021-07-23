package com.tkachuk.jobnetwork.service;

import com.tkachuk.jobnetwork.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Service interface for class {@link User}.
 *
 * @author Svitlana Tkachuk
 */

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);
}