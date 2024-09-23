package com.omniscient.omniscientback.manager.faq.repository;


import com.omniscient.omniscientback.manager.faq.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Integer> {

    // 상태가 활성화된 FAQ만 찾는 메서드
    List<Faq> findByStatusTrue();

    // 특정 상태를 기준으로 FAQ를 찾는 메서드
    Optional<Faq> findByFaqIdAndStatusTrue(Integer faqId);
}
