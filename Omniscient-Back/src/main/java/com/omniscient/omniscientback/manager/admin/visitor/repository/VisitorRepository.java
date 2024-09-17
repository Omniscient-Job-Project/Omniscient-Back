package com.omniscient.omniscientback.manager.admin.visitor.repository;




import com.omniscient.omniscientback.manager.admin.visitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    Visitor findByVisitDate(LocalDate visitDate);
}
