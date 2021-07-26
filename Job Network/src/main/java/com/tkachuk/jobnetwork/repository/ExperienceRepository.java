package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Experience}.
 *
 * @author Svitlana Tkachuk
 */

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
