package com.omniscient.omniscientback.manager.admin.visitor.service;

import com.omniscient.omniscientback.manager.admin.visitor.model.Visitor;
import com.omniscient.omniscientback.manager.admin.visitor.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public void trackVisitorToday() {
        LocalDate today = LocalDate.now();
        Visitor visitor = visitorRepository.findByVisitDate(today);
        if (visitor == null) {
            visitor = new Visitor();
            visitor.setVisitDate(today);
            visitor.setVisitCount(1);
        } else {
            visitor.setVisitCount(visitor.getVisitCount() + 1);
        }
        visitorRepository.save(visitor);
    }

    public Integer getTodayVisitorCount() {
        LocalDate today = LocalDate.now();
        Visitor visitor = visitorRepository.findByVisitDate(today);
        return (visitor != null) ? visitor.getVisitCount() : 0;
    }

    public List<Integer> getDailyVisitors() {
        LocalDate startDate = LocalDate.now().minusDays(30); // 최근 30일
        List<Visitor> visitors = visitorRepository.findAllByVisitDateBetween(startDate, LocalDate.now());

        return IntStream.range(0, 30)
                .mapToObj(i -> {
                    LocalDate date = LocalDate.now().minusDays(i);
                    return visitors.stream()
                            .filter(visitor -> visitor.getVisitDate().isEqual(date))
                            .map(Visitor::getVisitCount)
                            .findFirst()
                            .orElse(0);
                })
                .collect(Collectors.toList());
    }

    public List<Integer> getMonthlyVisitors() {
        LocalDate startDate = LocalDate.now().minusMonths(12); // 최근 12개월
        List<Visitor> visitors = visitorRepository.findAllByVisitDateBetween(startDate, LocalDate.now());

        return IntStream.range(0, 12)
                .mapToObj(i -> {
                    LocalDate date = LocalDate.now().minusMonths(i);
                    return visitors.stream()
                            .filter(visitor -> visitor.getVisitDate().getMonth() == date.getMonth())
                            .map(Visitor::getVisitCount)
                            .reduce(0, Integer::sum);
                })
                .collect(Collectors.toList());
    }
}
