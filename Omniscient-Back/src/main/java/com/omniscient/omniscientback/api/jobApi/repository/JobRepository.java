package com.omniscient.omniscientback.api.jobApi.repository;

import com.omniscient.omniscientback.api.jobApi.model.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Integer> {


}
