package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Company}.
 *
 * @author Svitlana Tkachuk
 */

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long id);
}
