package com.omniscient.omniscientback.manager.faq.controller;

import com.omniscient.omniscientback.manager.faq.model.FaqDTO;
import com.omniscient.omniscientback.manager.faq.service.FaqService;
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

    @GetMapping("/{faqId}")
    public ResponseEntity<FaqDTO> getFaqByFaqId(@PathVariable("faqId") Integer faqId) {
        FaqDTO faq = faqService.getFaqByFaqId(faqId);
        if (faq == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 상태 반환
        }
        return ResponseEntity.ok(faq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FaqDTO> createFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO createdFaq = faqService.createFaq(faqDTO);
        return ResponseEntity.ok(createdFaq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{faqId}")
    public ResponseEntity<FaqDTO> updateFaq(@PathVariable("faqId") Integer faqId, @RequestBody FaqDTO faqDTO) {
        FaqDTO updatedFaq = faqService.updateFaq(faqId, faqDTO);
        return ResponseEntity.ok(updatedFaq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/delete/{faqId}")
    public ResponseEntity<Boolean> deleteFaq(@PathVariable("faqId") Integer faqId) {
        boolean isDeleted = faqService.deleteFaq(faqId);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping("/views/{faqId}")
    public ResponseEntity<String> incrementViews(@PathVariable("faqId") Integer faqId) {
        try {
            faqService.incrementViews(faqId);
            return ResponseEntity.ok("조회수가 증가하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회수 증가 중 오류 발생");
        }
    }
}
