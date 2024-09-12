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
@CrossOrigin(origins = "http://localhost:8083")
public class ResumeController {
    private final ResumeService resumeService;
    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    public ResponseEntity<List<ResumeDTO>> getAllResumes() {
        logger.info("모든 활성화된 이력서 조회 요청");
        try {
            List<ResumeDTO> resumes = resumeService.getAllResumes();
            logger.info("이력서 조회 성공: {} 개 조회됨", resumes.size());
            return ResponseEntity.ok(resumes);
        } catch (Exception e) {
            logger.error("이력서 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDTO> getResume(@PathVariable Integer id) {
        logger.info("이력서 조회 요청: ID {}", id);
        try {
            ResumeDTO resume = resumeService.getResume(id);
            logger.info("이력서 조회 성공: ID {}", id);
            return ResponseEntity.ok(resume);
        } catch (RuntimeException e) {
            logger.warn("이력서를 찾을 수 없음: ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("이력서 조회 중 오류 발생: ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ResumeDTO> createResume(@RequestBody ResumeDTO resumeDTO) {
        logger.info("새 이력서 생성 요청");
        try {
            ResumeDTO createdResume = resumeService.createResume(resumeDTO);
            logger.info("새 이력서 생성 성공: ID {}", createdResume.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
        } catch (Exception e) {
            logger.error("새 이력서 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeDTO> updateResume(@PathVariable Integer id, @RequestBody ResumeDTO resumeDTO) {
        logger.info("이력서 업데이트 요청: ID {}", id);
        try {
            ResumeDTO updatedResume = resumeService.updateResume(id, resumeDTO);
            logger.info("이력서 업데이트 성공: ID {}", id);
            return ResponseEntity.ok(updatedResume);
        } catch (RuntimeException e) {
            logger.warn("업데이트할 이력서를 찾을 수 없음: ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("이력서 업데이트 중 오류 발생: ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateResume(@PathVariable Integer id) {
        logger.info("이력서 비활성화 요청: ID {}", id);
        try {
            resumeService.deactivateResume(id);
            logger.info("이력서 비활성화 성공: ID {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.warn("비활성화할 이력서를 찾을 수 없음: ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("이력서 비활성화 중 오류 발생: ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}