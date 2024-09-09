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
@RequestMapping("/api/mypage")
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
        logger.info("Fetching all profiles");
        try {
            List<ProfileDTO> profiles = profileService.getAllProfiles();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            logger.error("Error fetching all profiles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Integer id) {
        logger.info("Fetching profile for id: {}", id);
        try {
            ProfileDTO profile = profileService.getProfile(id);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            logger.warn("Profile not found for id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error fetching profile for id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> createProfile(@ModelAttribute ProfileDTO profileDTO,
                                                    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        logger.info("Creating new profile: {}", profileDTO);
        try {
            if (profileImage != null && !profileImage.isEmpty()) {
                profileDTO.setProfileImage(profileImage.getBytes());
            }
            // id가 null이거나 0이면 새로운 프로필 생성으로 간주
            if (profileDTO.getId() == null || profileDTO.getId() == 0) {
                profileDTO.setId((Integer) null);
            }
            ProfileDTO createdProfile = profileService.createProfile(profileDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (Exception e) {
            logger.error("Error creating new profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable Integer id,
                                                    @ModelAttribute ProfileDTO profileDTO,
                                                    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        logger.info("Updating profile for id: {}", id);
        logger.debug("Received profile data: {}", profileDTO);
        try {
            profileDTO.setId(id);  // 이 부분은 그대로 유지됩니다.
            if (profileImage != null && !profileImage.isEmpty()) {
                profileDTO.setProfileImage(profileImage.getBytes());
            }
            ProfileDTO updatedProfile = profileService.updateProfile(profileDTO);
            logger.info("Profile updated successfully for id: {}", id);
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            logger.warn("Profile not found for update, id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating profile for id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Integer id) {
        logger.info("Deleting profile for id: {}", id);
        try {
            profileService.deleteProfile(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Profile not found for deletion, id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting profile for id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}