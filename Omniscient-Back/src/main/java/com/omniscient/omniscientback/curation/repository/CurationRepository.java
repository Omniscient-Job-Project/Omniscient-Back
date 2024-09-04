package com.omniscient.omniscientback.curation.repository;

import com.omniscient.omniscientback.curation.model.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Integer> {

}
