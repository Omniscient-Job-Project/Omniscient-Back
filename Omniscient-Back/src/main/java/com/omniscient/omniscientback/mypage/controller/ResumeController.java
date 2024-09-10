package com.omniscient.omniscientback.mypage.controller;

import com.omniscient.omniscientback.mypage.model.ResumeDTO;
import com.omniscient.omniscientback.mypage.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage/resume")
@CrossOrigin(origins = "http://localhost:8083")
public class ResumeController {
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    /**
     * 모든 활성화된 이력서를 조회합니다.
     * @return 활성화된 이력서 목록
     */
    @GetMapping
    public ResponseEntity<List<ResumeDTO>> getAllResumes() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    /**
     * 특정 ID의 활성화된 이력서를 조회합니다.
     * @param id 조회할 이력서의 ID
     * @return 조회된 이력서
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResumeDTO> getResume(@PathVariable Integer id) {
        return ResponseEntity.ok(resumeService.getResume(id));
    }

    /**
     * 새로운 이력서를 생성합니다.
     * @param resumeDTO 생성할 이력서 정보
     * @return 생성된 이력서
     */
    @PostMapping
    public ResponseEntity<ResumeDTO> createResume(@RequestBody ResumeDTO resumeDTO) {
        return ResponseEntity.ok(resumeService.createResume(resumeDTO));
    }

    /**
     * 기존 이력서를 업데이트합니다.
     * @param id 업데이트할 이력서의 ID
     * @param resumeDTO 업데이트할 이력서 정보
     * @return 업데이트된 이력서
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResumeDTO> updateResume(@PathVariable Integer id, @RequestBody ResumeDTO resumeDTO) {
        return ResponseEntity.ok(resumeService.updateResume(id, resumeDTO));
    }

    /**
     * 이력서를 비활성화합니다. (삭제 대신 상태 변경)
     * @param id 비활성화할 이력서의 ID
     * @return 응답 엔티티
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateResume(@PathVariable Integer id) {
        resumeService.deactivateResume(id);
        return ResponseEntity.noContent().build();
    }
}