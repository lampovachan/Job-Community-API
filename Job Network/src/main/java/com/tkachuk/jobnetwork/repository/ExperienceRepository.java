package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
