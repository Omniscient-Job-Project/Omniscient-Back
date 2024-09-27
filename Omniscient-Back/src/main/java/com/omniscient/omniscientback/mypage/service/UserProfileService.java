package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.UserProfile;
import com.omniscient.omniscientback.mypage.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final String uploadDir = "./uploads"; // 실제 경로로 변경해야 함

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional(readOnly = true)
    public List<UserProfile> getAllActiveProfiles() {
        return userProfileRepository.findAllByActiveTrue();
    }

    @Transactional(readOnly = true)
    public UserProfile getProfileById(Integer id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid profile Id:" + id));
    }

    @Transactional
    public UserProfile createProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Transactional
    public UserProfile updateProfile(UserProfile userProfile) {
        UserProfile existingProfile = getProfileById(userProfile.getId());
        // Update fields
        existingProfile.setName(userProfile.getName());
        existingProfile.setEmail(userProfile.getEmail());
        existingProfile.setImageFileName(userProfile.getImageFileName());
        // Update other fields as necessary
        return userProfileRepository.save(existingProfile);
    }

    @Transactional
    public void deactivateProfile(Integer id) {
        UserProfile profile = getProfileById(id);
        profile.setActive(false);
        userProfileRepository.save(profile);
    }

    @Transactional
    public UserProfile updateProfileImage(Integer profileId, MultipartFile file) throws IOException {
        UserProfile profile = getProfileById(profileId);
        String fileName = saveFile(file);
        profile.setImageFileName(fileName);
        return userProfileRepository.save(profile);
    }

    public byte[] getProfileImageBytes(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        return Files.readAllBytes(filePath);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }
}