package com.omniscient.omniscientback.manager.admin.visitor.repository;

import com.omniscient.omniscientback.manager.admin.visitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    Visitor findByVisitDate(LocalDate visitDate);

    List<Visitor> findAllByVisitDateBetween(LocalDate startDate, LocalDate endDate); // 날짜 범위 검색
}
