package com.omniscient.omniscientback.api.employmentapi.repository;

import com.omniscient.omniscientback.api.employmentapi.model.EmploymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<EmploymentEntity, Integer> {
}
