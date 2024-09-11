package com.omniscient.omniscientback.api.gradejob.repository;

import com.omniscient.omniscientback.api.gradejob.model.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeApiRepository extends JpaRepository<GradeEntity, Integer> {
}
