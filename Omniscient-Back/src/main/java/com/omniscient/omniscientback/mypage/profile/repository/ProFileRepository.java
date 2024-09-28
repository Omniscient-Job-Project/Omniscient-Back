package com.omniscient.omniscientback.mypage.profile.repository;

import com.omniscient.omniscientback.mypage.profile.model.ProFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProFileRepository extends JpaRepository<ProFileEntity, Integer> {

    // 활성 상태인 프로필만 조회
//    List<ProFileEntity> findByActiveTrue();
}
