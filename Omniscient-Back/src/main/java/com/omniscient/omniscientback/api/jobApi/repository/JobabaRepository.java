package com.omniscient.omniscientback.api.jobApi.repository;

import com.omniscient.omniscientback.api.jobApi.model.JobabaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobabaRepository extends JpaRepository<JobabaEntity, Integer> {

    Optional<JobabaEntity> findByJobabaId(Integer jobabaId);

}
