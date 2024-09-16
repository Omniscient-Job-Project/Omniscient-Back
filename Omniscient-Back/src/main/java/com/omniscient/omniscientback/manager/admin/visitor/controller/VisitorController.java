package com.omniscient.omniscientback.manager.admin.visitor.controller;


import com.omniscient.omniscientback.manager.admin.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/visitor-today")
    public ResponseEntity<Void> trackVisitorToday() {
        visitorService.trackVisitorToday();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/visitor-today")
    public ResponseEntity<Object> getTodayVisitorCount() {
        Integer count = visitorService.getTodayVisitorCount();
        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/dailyVisitors")
    public ResponseEntity<Object> getDailyVisitors() {
        return ResponseEntity.ok().body(visitorService.getDailyVisitors());
    }

    @GetMapping("/monthlyVisitors")
    public ResponseEntity<Object> getMonthlyVisitors() {
        return ResponseEntity.ok().body(visitorService.getMonthlyVisitors());
    }
}
