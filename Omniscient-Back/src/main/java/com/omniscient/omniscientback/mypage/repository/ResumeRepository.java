package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    /**
     * 활성화된 모든 이력서를 조회합니다.
     * @return 활성화된 이력서 목록
     */
    List<Resume> findAllByStatusTrue();
    /**
     * 주어진 ID와 활성화 상태인 이력서를 조회합니다.
     * @param resumeId 조회할 이력서의 ID
     * @return 활성화된 이력서 (Optional)
     */

    Optional<Resume> findByResumeIdAndStatusTrue(Integer resumeId);

    /**
     * 주어진 이름과 활성화 상태인 이력서를 조회합니다.
     * @param name 조회할 이력서 소유자의 이름
     * @return 활성화된 이력서 목록
     */
    List<Resume> findByNameAndStatusTrue(String name);

    /**
     * 주어진 이메일과 활성화 상태인 이력서를 조회합니다.
     * @param email 조회할 이력서 소유자의 이메일
     * @return 활성화된 이력서 (Optional)
     */
    Optional<Resume> findByEmailAndStatusTrue(String email);

    /**
     * 활성화된 이력서의 수를 계산합니다.
     * @return 활성화된 이력서의 수
     */
    Integer countByStatusTrue();

    /**
     * 주어진 제목을 포함하고 활성화 상태인 이력서를 조회합니다.
     * @param title 검색할 제목 (부분 일치)
     * @return 활성화된 이력서 목록
     */
    List<Resume> findByTitleContainingAndStatusTrue(String title);
}