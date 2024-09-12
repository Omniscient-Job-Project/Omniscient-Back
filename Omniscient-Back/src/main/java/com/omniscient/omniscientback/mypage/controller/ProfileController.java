package com.omniscient.omniscientback.mypage.controller;

import com.omniscient.omniscientback.mypage.model.ProfileDTO;
import com.omniscient.omniscientback.mypage.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@CrossOrigin(origins = "http://localhost:8083")
public class ProfileController {
    private final ProfileService profileService;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        logger.info("모든 활성화된 프로필 조회");
        try {
            List<ProfileDTO> profiles = profileService.getAllProfiles();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            logger.error("모든 프로필 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Integer id) {
        logger.info("활성화된 프로필 조회: ID {}", id);
        try {
            ProfileDTO profile = profileService.getProfile(id);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            logger.warn("활성화된 프로필을 찾을 수 없음: ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("프로필 조회 중 오류 발생: ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> createProfile(@ModelAttribute ProfileDTO profileDTO,
                                                    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        logger.info("새 프로필 생성: {}", profileDTO);
        try {
            if (profileImage != null && !profileImage.isEmpty()) {
                profileDTO.setProfileImage(profileImage.getBytes());
            }
            ProfileDTO createdProfile = profileService.createProfile(profileDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (Exception e) {
            logger.error("새 프로필 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable Integer id,
                                                    @ModelAttribute ProfileDTO profileDTO,
                                                    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        logger.info("프로필 업데이트: ID {}", id);
        logger.debug("수신된 프로필 데이터: {}", profileDTO);
        try {
            profileDTO.setId(id);
            if (profileImage != null && !profileImage.isEmpty()) {
                profileDTO.setProfileImage(profileImage.getBytes());
            }
            ProfileDTO updatedProfile = profileService.updateProfile(profileDTO);
            logger.info("프로필 업데이트 성공: ID {}", id);
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            logger.warn("업데이트할 활성화된 프로필을 찾을 수 없음: ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("프로필 업데이트 중 오류 발생: ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateProfile(@PathVariable Integer id) {
        logger.info("프로필 비활성화: ID {}", id);
        try {
            profileService.deactivateProfile(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("비활성화할 프로필을 찾을 수 없음: ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("프로필 비활성화 중 오류 발생: ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}