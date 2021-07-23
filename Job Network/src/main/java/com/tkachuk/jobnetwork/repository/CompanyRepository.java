package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Company}.
 *
 * @author Svitlana Tkachuk
 */

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long id);
}
