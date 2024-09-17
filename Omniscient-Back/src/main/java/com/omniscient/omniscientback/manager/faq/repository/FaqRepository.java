package com.omniscient.omniscientback.manager.faq.repository;


import com.omniscient.omniscientback.manager.faq.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
}
