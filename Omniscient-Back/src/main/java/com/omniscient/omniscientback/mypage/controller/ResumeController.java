package com.omniscient.omniscientback.mypage.controller;

import com.omniscient.omniscientback.mypage.model.ResumeDTO;
import com.omniscient.omniscientback.mypage.service.ResumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    public ResponseEntity<List<ResumeDTO>> getAllResumes() {
        try {
            List<ResumeDTO> resumes = resumeService.getAllResumes();
            return ResponseEntity.ok(resumes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeDTO> getResume(@PathVariable Integer resumeId) {
        try {
            ResumeDTO resume = resumeService.getResume(resumeId);
            return ResponseEntity.ok(resume);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ResumeDTO> createResume(@RequestBody ResumeDTO resumeDTO) {
        try {
            if (resumeDTO.getTitle() == null || resumeDTO.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            ResumeDTO createdResume = resumeService.createResume(resumeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{resumeId}")
    public ResponseEntity<ResumeDTO> updateResume(@PathVariable Integer resumeId, @RequestBody ResumeDTO resumeDTO) {
        try {
            ResumeDTO existingResume = resumeService.getResume(resumeId);
            if (existingResume == null) {
                return ResponseEntity.notFound().build();
            }
            // null이 아닌 필드만 업데이트
            if (resumeDTO.getTitle() != null) existingResume.setTitle(resumeDTO.getTitle());
            if (resumeDTO.getName() != null) existingResume.setName(resumeDTO.getName());
            if (resumeDTO.getEmail() != null) existingResume.setEmail(resumeDTO.getEmail());
            if (resumeDTO.getPhone() != null) existingResume.setPhone(resumeDTO.getPhone());
            if (resumeDTO.getEducation() != null) existingResume.setEducation(resumeDTO.getEducation());
            if (resumeDTO.getExperience() != null) existingResume.setExperience(resumeDTO.getExperience());
            if (resumeDTO.getSkills() != null) existingResume.setSkills(resumeDTO.getSkills());
            if (resumeDTO.getCertificates() != null) existingResume.setCertificates(resumeDTO.getCertificates());
            if (resumeDTO.getIntroduction() != null) existingResume.setIntroduction(resumeDTO.getIntroduction());

            ResumeDTO updatedResume = resumeService.updateResume(resumeId, existingResume);
            return ResponseEntity.ok(updatedResume);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{resumeId}/deactivate")
    public ResponseEntity<Void> deactivateResume(@PathVariable Integer resumeId) {
        try {
            resumeService.deactivateResume(resumeId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
