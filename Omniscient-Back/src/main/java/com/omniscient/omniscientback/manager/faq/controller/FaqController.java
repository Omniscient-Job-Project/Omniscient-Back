package com.omniscient.omniscientback.manager.faq.controller;

import com.omniscient.omniscientback.manager.faq.model.FaqDTO;
import com.omniscient.omniscientback.manager.faq.service.FaqService;
import com.omniscient.omniscientback.manager.notice.model.Notice;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faqs")
public class FaqController {

    private final FaqService faqService;

    @Autowired
    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<List<FaqDTO>> getAllFaqs() {
        List<FaqDTO> faqs = faqService.getAllFaqs();
        return ResponseEntity.ok(faqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqDTO> getFaqById(@PathVariable("id") Integer id) {
        FaqDTO faq = faqService.getFaqById(id);
        if (faq == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 상태 반환
        }
        return ResponseEntity.ok(faq);
    }
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능

    @PostMapping
    public ResponseEntity<FaqDTO> createFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO createdFaq = faqService.createFaq(faqDTO);
        return ResponseEntity.ok(createdFaq);
    }
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    @PutMapping("/update/{id}")
    public ResponseEntity<FaqDTO> updateFaq(@PathVariable("id") Integer id, @RequestBody FaqDTO faqDTO) {
        FaqDTO updatedFaq = faqService.updateFaq(id, faqDTO);
        return ResponseEntity.ok(updatedFaq);
    }
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    @PutMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteFaq(@PathVariable("id") Integer id) {
        boolean isDeleted = faqService.deleteFaq(id);
        return ResponseEntity.ok(isDeleted);
    }
    @PutMapping("/views/{id}")
    public ResponseEntity<String> incrementViews(@PathVariable("id") Integer id) {
        try {
            faqService.incrementViews(id);
            return ResponseEntity.ok("조회수가 증가하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회수 증가 중 오류 발생");
        }

    }
}
