package com.omniscient.omniscientback.api.categoryapi.repository;

import com.omniscient.omniscientback.api.categoryapi.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
