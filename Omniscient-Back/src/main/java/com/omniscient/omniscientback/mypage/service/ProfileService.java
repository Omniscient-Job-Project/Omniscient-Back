package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.ProfileDTO;
import com.omniscient.omniscientback.mypage.model.Profile;
import com.omniscient.omniscientback.mypage.repository.ProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB in bytes

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<ProfileDTO> getAllProfiles() {
        return profileRepository.findAllByStatusTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProfileDTO getProfile(Integer id) {
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));
        return convertToDTO(profile);
    }

    @Transactional
    public ProfileDTO createProfile(ProfileDTO profileDTO, List<MultipartFile> profileImages) throws IOException {
        Profile profile = convertToEntity(profileDTO);
        profile.setStatus(true);
        if (profileImages != null && !profileImages.isEmpty()) {
            List<byte[]> imageBytesList = new ArrayList<>();
            for (MultipartFile image : profileImages) {
                if (image.getSize() > MAX_FILE_SIZE) {
                    throw new IllegalArgumentException("이미지 크기가 5MB를 초과합니다.");
                }
                imageBytesList.add(image.getBytes());
            }
            profile.setProfileImages(imageBytesList);
        }
        Profile savedProfile = profileRepository.save(profile);
        return convertToDTO(savedProfile);
    }

    @Transactional
    public ProfileDTO updateProfile(ProfileDTO profileDTO, List<MultipartFile> profileImages) throws IOException {
        Profile existingProfile = profileRepository.findByIdAndStatusTrue(profileDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));
        updateProfileFields(existingProfile, profileDTO);
        if (profileImages != null && !profileImages.isEmpty()) {
            List<byte[]> imageBytesList = new ArrayList<>();
            for (MultipartFile image : profileImages) {
                if (image.getSize() > MAX_FILE_SIZE) {
                    throw new IllegalArgumentException("이미지 크기가 5MB를 초과합니다.");
                }
                imageBytesList.add(image.getBytes());
            }
            existingProfile.setProfileImages(imageBytesList);
        }
        Profile updatedProfile = profileRepository.save(existingProfile);
        return convertToDTO(updatedProfile);
    }

    @Transactional
    public void deactivateProfile(Integer id) {
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));
        profile.setStatus(false);
        profileRepository.save(profile);
    }

    private ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        BeanUtils.copyProperties(profile, dto);
        if (profile.getProfileImages() != null && !profile.getProfileImages().isEmpty()) {
            dto.setProfileImageBase64(profile.getProfileImages().stream()
                    .map(imageBytes -> Base64.getEncoder().encodeToString(imageBytes))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
    private Profile convertToEntity(ProfileDTO dto) {
        Profile profile = new Profile();
        BeanUtils.copyProperties(dto, profile);
        return profile;
    }

    private void updateProfileFields(Profile profile, ProfileDTO dto) {
        profile.setName(dto.getName());
        profile.setJobTitle(dto.getJobTitle());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setAge(dto.getAge());
        profile.setAddress(dto.getAddress());
        profile.setCertificates(dto.getCertificates());
    }
}