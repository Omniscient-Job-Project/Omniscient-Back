package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    List<Certificate> findAllByIsActiveTrue();

    Optional<Certificate> findByIdAndIsActiveTrue(Integer id);

    Optional<Certificate> findByNameAndIsActiveTrue(String name);

    Integer countByIsActiveTrue();

    /**
     * 주어진 ID의 자격증을 찾아 isActive 상태를 업데이트합니다.
     *
     * @Modifying: 이 어노테이션은 해당 쿼리가 데이터를 변경한다는 것을 JPA에 알립니다.
     *             SELECT 쿼리가 아닌 INSERT, UPDATE, DELETE와 같은 DML 연산에 사용됩니다.
     *
     * @Transactional: 이 어노테이션은 메서드 실행을 트랜잭션으로 감싸, 작업의 원자성을 보장합니다.
     *                 데이터베이스 상태를 변경하는 작업에서 데이터 일관성을 유지하는 데 중요합니다.
     *
     * @Query: 이 어노테이션을 사용하여 직접 JPQL(Java Persistence Query Language) 쿼리를 정의합니다.
     *         메서드 이름으로는 표현하기 어려운 복잡한 쿼리나 특정 데이터베이스 기능을 사용할 때 유용합니다.
     *         여기서는 특정 ID를 가진 Certificate의 isActive 상태를 업데이트하는 쿼리를 정의합니다.
     *
     * 이 방식을 사용하는 이유:
     * 1. 메서드 이름으로 updateIsActiveById와 같은 복잡한 연산을 표현하기 어렵습니다.
     * 2. Spring Data JPA가 메서드 이름을 파싱하여 자동으로 쿼리를 생성하는 방식으로는
     *    이러한 특정 업데이트 연산을 정확히 표현하기 어렵습니다.
     * 3. 직접 쿼리를 작성함으로써 정확히 원하는 연산을 수행할 수 있습니다.
     * 4. @Modifying과 @Transactional을 사용하여 데이터 변경 작업의 안정성을 보장합니다.
     *
     * @param id 업데이트할 자격증의 ID
     * @param isActive 설정할 활성화 상태
     * @return 업데이트된 자격증의 수 (보통 0 또는 1)
     */
    @Modifying
    @Transactional
    @Query("UPDATE Certificate c SET c.isActive = :isActive WHERE c.id = :id")
    int updateIsActiveById(Integer id, Boolean isActive);
}