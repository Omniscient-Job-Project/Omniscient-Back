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
@RequestMapping("/api/v1/mypage/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        try {
            List<ProfileDTO> profiles = profileService.getAllProfiles();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable("id") Integer id) {
        try {
            ProfileDTO profile = profileService.getProfile(id);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> createProfile(
            @ModelAttribute ProfileDTO profileDTO,
            @RequestParam(value = "profileImages", required = false) List<MultipartFile> profileImages) {
        try {
            ProfileDTO createdProfile = profileService.createProfile(profileDTO, profileImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> updateProfile(
            @PathVariable("id") Integer id,
            @ModelAttribute ProfileDTO profileDTO,
            @RequestParam(value = "profileImages", required = false) List<MultipartFile> profileImages) {
        try {
            profileDTO.setProfileId(id);  // setId -> setProfileId
            ProfileDTO updatedProfile = profileService.updateProfile(profileDTO, profileImages);
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateProfile(@PathVariable("id") Integer id) {
        try {
            profileService.deactivateProfile(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

