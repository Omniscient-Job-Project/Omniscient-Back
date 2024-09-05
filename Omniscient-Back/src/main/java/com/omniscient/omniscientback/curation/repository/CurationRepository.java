package com.omniscient.omniscientback.curation.repository;

import com.omniscient.omniscientback.curation.model.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Integer> {

    List<Curation> findByTitleNameInfoContaining(String term, String term1, String term2);

}
