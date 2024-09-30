package com.omniscient.omniscientback.manager.admin.visitor.controller;

import com.omniscient.omniscientback.manager.admin.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/todayVisitor")
    public ResponseEntity<Void> trackVisitorToday() {
        try {
            visitorService.trackVisitorToday();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/todayVisitor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> getTodayVisitorCount() {
        try {
            Integer count = visitorService.getTodayVisitorCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dailyVisitors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Integer>> getDailyVisitors() {
        try {
            return ResponseEntity.ok(visitorService.getDailyVisitors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/monthlyVisitors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Integer>> getMonthlyVisitors() {
        try {
            return ResponseEntity.ok(visitorService.getMonthlyVisitors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
