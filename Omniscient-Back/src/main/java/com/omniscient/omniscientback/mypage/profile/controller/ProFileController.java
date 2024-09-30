package com.omniscient.omniscientback.mypage.profile.controller;

import com.omniscient.omniscientback.mypage.profile.model.ProFileDTO;
import com.omniscient.omniscientback.mypage.profile.model.ProFileEntity;
import com.omniscient.omniscientback.mypage.profile.service.ProFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/profile")
public class ProFileController {

    private final ProFileService proFileService;

    @Autowired
    public ProFileController(ProFileService proFileService) {
        this.proFileService = proFileService;
    }

    @GetMapping
    public ResponseEntity<List<ProFileEntity>> getAllProfile() {
        return ResponseEntity.ok(proFileService.getAllProfiles());
    }

    // ID로 프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProFileEntity> getProfileById(@PathVariable Integer id) {
        return proFileService.getProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 프로필 생성
    @PostMapping
    public ResponseEntity<ProFileEntity> createProfile(@RequestBody ProFileEntity proFileEntity) {
        return ResponseEntity.ok(proFileService.createProfile(proFileEntity));
    }


    // 프로필 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<ProFileEntity> updateProfile(@PathVariable Integer id, @RequestBody ProFileDTO proFileDTO) {
        return ResponseEntity.ok(proFileService.updateProfile(id, proFileDTO));
    }

    // 프로필 상태 비활성화
    @PutMapping("/status/{id}")
    public ResponseEntity<Void> deactivateProfile(@PathVariable Integer id) {
        proFileService.deleteProFileById(id);
        return ResponseEntity.noContent().build();
    }
}
