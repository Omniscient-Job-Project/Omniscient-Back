package com.omniscient.omniscientback.manager.faq.controller;

import com.omniscient.omniscientback.manager.faq.model.FaqDTO;
import com.omniscient.omniscientback.manager.faq.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faqs")
@CrossOrigin(origins = "http://localhost:8083")
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
    public ResponseEntity<FaqDTO> getFaqById(@PathVariable Integer id) {
        FaqDTO faq = faqService.getFaqById(id);
        return ResponseEntity.ok(faq);
    }

    @PostMapping
    public ResponseEntity<FaqDTO> createFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO createdFaq = faqService.createFaq(faqDTO);
        return ResponseEntity.ok(createdFaq);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FaqDTO> updateFaq(@PathVariable Integer id, @RequestBody FaqDTO faqDTO) {
        FaqDTO updatedFaq = faqService.updateFaq(id, faqDTO);
        return ResponseEntity.ok(updatedFaq);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteFaq(@PathVariable Integer id) {
        boolean isDeleted = faqService.deleteFaq(id);
        return ResponseEntity.ok(isDeleted);
    }
}
