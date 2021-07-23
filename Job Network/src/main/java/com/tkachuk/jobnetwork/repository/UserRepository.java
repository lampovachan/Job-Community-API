package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link User}.
 *
 * @author Svitlana Tkachuk
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
