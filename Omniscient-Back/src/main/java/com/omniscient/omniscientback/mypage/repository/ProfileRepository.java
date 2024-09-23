package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    /**
     * status가 true인 모든 프로필을 조회합니다.
     * @return 활성화된 프로필 목록
     */
    List<Profile> findAllByStatusTrue();

    /**
     * 주어진 ID와 status가 true인 프로필을 조회합니다.
     * @param profileId 조회할 프로필의 ID
     * @return 활성화된 프로필 (Optional)
     */
    Optional<Profile> findByProfileIdAndStatusTrue(Integer profileId);  // 수정된 부분

    /**
     * 주어진 이메일과 status가 true인 프로필을 조회합니다.
     * @param email 조회할 프로필의 이메일
     * @return 활성화된 프로필 (Optional)
     */
    Optional<Profile> findByEmailAndStatusTrue(String email);
    /**
     * status가 true인 프로필의 수를 계산합니다.
     * @return 활성화된 프로필의 수
     */
    Integer countByStatusTrue();
}