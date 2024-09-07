package com.omniscient.omniscientback.manager.notice.repository;

import com.omniscient.omniscientback.manager.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

}