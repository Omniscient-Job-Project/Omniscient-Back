package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    // 필요한 경우 커스텀 쿼리 메서드 추가
}