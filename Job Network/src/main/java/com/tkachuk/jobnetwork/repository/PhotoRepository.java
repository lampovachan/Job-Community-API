package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.Photo;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface that extends {@link CrudRepository} for class {@link Photo}.
 *
 * @author Svitlana Tkachuk
 */

public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
