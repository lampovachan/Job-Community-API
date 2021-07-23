package com.tkachuk.jobnetwork.repository;

import com.tkachuk.jobnetwork.model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
