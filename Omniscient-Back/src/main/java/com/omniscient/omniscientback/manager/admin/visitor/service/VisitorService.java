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
        // 여기에 일별 방문자 수를 가져오는 로직을 구현합니다.
        // 예를 들어, 최근 한 달 동안의 방문자 수를 반환할 수 있습니다.
        return IntStream.range(1, 32).map(i -> 0).boxed().collect(Collectors.toList());
    }

    public List<Integer> getMonthlyVisitors() {
        // 여기에 월별 방문자 수를 가져오는 로직을 구현합니다.
        // 예를 들어, 최근 12개월 동안의 방문자 수를 반환할 수 있습니다.
        return IntStream.range(1, 13).map(i -> 0).boxed().collect(Collectors.toList());
    }
}
