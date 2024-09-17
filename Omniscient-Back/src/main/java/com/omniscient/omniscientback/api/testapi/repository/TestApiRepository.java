package com.omniscient.omniscientback.api.testapi.repository;

import com.omniscient.omniscientback.api.testapi.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestApiRepository extends JpaRepository<TestEntity, Integer> {
}
