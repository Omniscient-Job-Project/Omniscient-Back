package com.omniscient.omniscientback.api.JobabaAPI.repository;

import com.omniscient.omniscientback.api.JobabaAPI.model.JobabaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobabaRepository extends JpaRepository<JobabaEntity, Integer> {

    Optional<JobabaEntity> findByJobabaId(Integer jobabaId);

}
