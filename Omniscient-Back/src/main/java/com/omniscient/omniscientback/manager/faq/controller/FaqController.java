package com.omniscient.omniscientback.manager.faq.controller;

import com.omniscient.omniscientback.manager.faq.model.FaqDTO;
import com.omniscient.omniscientback.manager.faq.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// test
@RestController
@RequestMapping("/api/v1/faqs")
@CrossOrigin(origins = "http://localhost:8083")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @GetMapping
    public ResponseEntity<List<FaqDTO>> getAllFaqs() {
        List<FaqDTO> faqs = faqService.getAllFaqs();
        return ResponseEntity.ok(faqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqDTO> getFaqById(@PathVariable Long id) {
        FaqDTO faq = faqService.getFaqById(id);
        return ResponseEntity.ok(faq);
    }

    @PostMapping
    public ResponseEntity<FaqDTO> createFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO createdFaq = faqService.createFaq(faqDTO);
        return ResponseEntity.ok(createdFaq);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaqDTO> updateFaq(@PathVariable Long id, @RequestBody FaqDTO faqDTO) {
        FaqDTO updatedFaq = faqService.updateFaq(id, faqDTO);
        return ResponseEntity.ok(updatedFaq);
    }

    // 삭제 후 상태를 false로 반환
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFaq(@PathVariable Long id) {
        boolean isDeleted = faqService.deleteFaq(id);
        return ResponseEntity.ok(isDeleted);  // 삭제 성공 여부를 반환
    }
}
