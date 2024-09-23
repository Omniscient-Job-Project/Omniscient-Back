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
                .map(profile -> {
                    ProfileDTO dto = new ProfileDTO();
                    dto.setProfileId(profile.getProfileId());
                    dto.setName(profile.getName());
                    dto.setJobTitle(profile.getJobTitle());
                    dto.setEmail(profile.getEmail());
                    dto.setPhone(profile.getPhone());
                    dto.setAge(profile.getAge());
                    dto.setAddress(profile.getAddress());
                    dto.setCertificates(profile.getCertificates());
                    dto.setStatus(profile.getStatus());

                    // 이미지 데이터를 Base64로 변환
                    if (profile.getProfileImages() != null && !profile.getProfileImages().isEmpty()) {
                        List<String> base64Images = profile.getProfileImages().stream()
                                .map(imageBytes -> Base64.getEncoder().encodeToString(imageBytes))
                                .collect(Collectors.toList());
                        dto.setProfileImageBase64(base64Images);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ProfileDTO getProfile(Integer id) {
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));

        ProfileDTO dto = new ProfileDTO();
        dto.setProfileId(profile.getProfileId());
        dto.setName(profile.getName());
        dto.setJobTitle(profile.getJobTitle());
        dto.setEmail(profile.getEmail());
        dto.setPhone(profile.getPhone());
        dto.setAge(profile.getAge());
        dto.setAddress(profile.getAddress());
        dto.setCertificates(profile.getCertificates());
        dto.setStatus(profile.getStatus());

        // 이미지 데이터를 Base64로 변환
        if (profile.getProfileImages() != null && !profile.getProfileImages().isEmpty()) {
            List<String> base64Images = profile.getProfileImages().stream()
                    .map(imageBytes -> Base64.getEncoder().encodeToString(imageBytes))
                    .collect(Collectors.toList());
            dto.setProfileImageBase64(base64Images);
        }
        return dto;
    }

    @Transactional
    public ProfileDTO createProfile(ProfileDTO profileDTO, List<MultipartFile> profileImages) throws IOException {
        Profile profile = new Profile();
        profile.setName(profileDTO.getName());
        profile.setJobTitle(profileDTO.getJobTitle());
        profile.setEmail(profileDTO.getEmail());
        profile.setPhone(profileDTO.getPhone());
        profile.setAge(profileDTO.getAge());
        profile.setAddress(profileDTO.getAddress());
        profile.setCertificates(profileDTO.getCertificates());
        profile.setStatus(true); // 기본 활성화 상태 설정

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

        // 저장된 엔티티를 DTO로 수동 매핑
        ProfileDTO createdProfileDTO = new ProfileDTO();
        createdProfileDTO.setProfileId(savedProfile.getProfileId());
        createdProfileDTO.setName(savedProfile.getName());
        createdProfileDTO.setJobTitle(savedProfile.getJobTitle());
        createdProfileDTO.setEmail(savedProfile.getEmail());
        createdProfileDTO.setPhone(savedProfile.getPhone());
        createdProfileDTO.setAge(savedProfile.getAge());
        createdProfileDTO.setAddress(savedProfile.getAddress());
        createdProfileDTO.setCertificates(savedProfile.getCertificates());
        createdProfileDTO.setStatus(savedProfile.getStatus());

        // 이미지 데이터를 Base64로 변환
        if (savedProfile.getProfileImages() != null && !savedProfile.getProfileImages().isEmpty()) {
            List<String> base64Images = savedProfile.getProfileImages().stream()
                    .map(imageBytes -> Base64.getEncoder().encodeToString(imageBytes))
                    .collect(Collectors.toList());
            createdProfileDTO.setProfileImageBase64(base64Images);
        }
        return createdProfileDTO;
    }

    @Transactional
    public ProfileDTO updateProfile(ProfileDTO profileDTO, List<MultipartFile> profileImages) throws IOException {
        Profile existingProfile = profileRepository.findByIdAndStatusTrue(profileDTO.getProfileId())
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));

        existingProfile.setName(profileDTO.getName());
        existingProfile.setJobTitle(profileDTO.getJobTitle());
        existingProfile.setEmail(profileDTO.getEmail());
        existingProfile.setPhone(profileDTO.getPhone());
        existingProfile.setAge(profileDTO.getAge());
        existingProfile.setAddress(profileDTO.getAddress());
        existingProfile.setCertificates(profileDTO.getCertificates());

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

        // 업데이트된 엔티티를 DTO로 수동 매핑
        ProfileDTO updatedProfileDTO = new ProfileDTO();
        updatedProfileDTO.setProfileId(updatedProfile.getProfileId());
        updatedProfileDTO.setName(updatedProfile.getName());
        updatedProfileDTO.setJobTitle(updatedProfile.getJobTitle());
        updatedProfileDTO.setEmail(updatedProfile.getEmail());
        updatedProfileDTO.setPhone(updatedProfile.getPhone());
        updatedProfileDTO.setAge(updatedProfile.getAge());
        updatedProfileDTO.setAddress(updatedProfile.getAddress());
        updatedProfileDTO.setCertificates(updatedProfile.getCertificates());
        updatedProfileDTO.setStatus(updatedProfile.getStatus());

        // 이미지 데이터를 Base64로 변환
        if (updatedProfile.getProfileImages() != null && !updatedProfile.getProfileImages().isEmpty()) {
            List<String> base64Images = updatedProfile.getProfileImages().stream()
                    .map(imageBytes -> Base64.getEncoder().encodeToString(imageBytes))
                    .collect(Collectors.toList());
            updatedProfileDTO.setProfileImageBase64(base64Images);
        }
        return updatedProfileDTO;
    }

    @Transactional
    public void deactivateProfile(Integer id) {
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));
        profile.setStatus(false);
        profileRepository.save(profile);
    }
}

