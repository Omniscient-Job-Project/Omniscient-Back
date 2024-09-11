package com.omniscient.omniscientback.api.siteapi.repository;

import com.omniscient.omniscientback.api.siteapi.model.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteApiRepository extends JpaRepository<SiteEntity, Integer> {
}
