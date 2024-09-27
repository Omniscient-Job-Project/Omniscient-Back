package com.omniscient.omniscientback.mypage.controller;

import com.omniscient.omniscientback.mypage.model.UserProfile;
import com.omniscient.omniscientback.mypage.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage/profile")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserProfile>> getAllProfiles() {
        try {
            List<UserProfile> profiles = userProfileService.getAllActiveProfiles();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            logger.error("프로필 목록 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getProfile(@PathVariable Integer id) {
        try {
            UserProfile profile = userProfileService.getProfileById(id);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            logger.warn("프로필 조회 실패: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("프로필 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> createProfile(@RequestBody UserProfile profile) {
        try {
            UserProfile createdProfile = userProfileService.createProfile(profile);
            logger.info("새 프로필 생성: ID {}", createdProfile.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (Exception e) {
            logger.error("프로필 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> updateProfile(@PathVariable Integer id, @RequestBody UserProfile profile) {
        try {
            profile.setId(id);
            UserProfile updatedProfile = userProfileService.updateProfile(profile);
            logger.info("프로필 업데이트: ID {}", updatedProfile.getId());
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            logger.warn("프로필 업데이트 실패: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("프로필 업데이트 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateProfile(@PathVariable Integer id) {
        try {
            userProfileService.deactivateProfile(id);
            logger.info("프로필 비활성화: ID {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("프로필 비활성화 실패: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("프로필 비활성화 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<UserProfile> uploadProfileImage(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        logger.info("프로필 이미지 업로드 요청 받음: 프로필 ID = {}, 파일 이름 = {}, 크기 = {} bytes", id, file.getOriginalFilename(), file.getSize());
        try {
            UserProfile updatedProfile = userProfileService.updateProfileImage(id, file);
            logger.info("프로필 이미지 업로드 성공: 프로필 ID = {}, 새 이미지 파일 이름 = {}", id, updatedProfile.getImageFileName());
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            logger.warn("프로필 이미지 업로드 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("프로필 이미지 업로드 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Integer id) {
        try {
            UserProfile profile = userProfileService.getProfileById(id);
            if (profile.getImageFileName() == null) {
                return ResponseEntity.notFound().build();
            }
            byte[] imageBytes = userProfileService.getProfileImageBytes(profile.getImageFileName());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 또는 적절한 이미지 타입
                    .body(imageBytes);
        } catch (IllegalArgumentException e) {
            logger.warn("프로필 이미지 조회 실패: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            logger.error("프로필 이미지 조회 중 I/O 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("프로필 이미지 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}