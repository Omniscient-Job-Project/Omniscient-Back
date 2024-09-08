package com.omniscient.omniscientback.curation.repository;

import com.omniscient.omniscientback.curation.model.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Integer> {

    // curationTitle 또는 curationContents에 term이 포함된 항목을 검색하는 메서드
    List<Curation> findByCurationTitleContainingOrCurationContentsContaining(String term1, String term2);

}
