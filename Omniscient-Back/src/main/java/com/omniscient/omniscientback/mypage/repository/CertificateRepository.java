package com.omniscient.omniscientback.mypage.repository;
import com.omniscient.omniscientback.mypage.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    /**
     * isActive가 true인 모든 자격증을 조회합니다.
     * @return 활성화된 자격증 목록
     */
    List<Certificate> findAllByIsActiveTrue();

    /**
     * 주어진 ID와 isActive가 true인 자격증을 조회합니다.
     * @param id 조회할 자격증의 ID
     * @return 활성화된 자격증 (Optional)
     */
    Optional<Certificate> findByIdAndIsActiveTrue(Integer id);

    /**
     * 주어진 이름과 isActive가 true인 자격증을 조회합니다.
     * @param name 조회할 자격증의 이름
     * @return 활성화된 자격증 (Optional)
     */
    Optional<Certificate> findByNameAndIsActiveTrue(String name);

    /**
     * isActive가 true인 자격증의 수를 계산합니다.
     * @return 활성화된 자격증의 수
     */
    Integer countByIsActiveTrue();
}