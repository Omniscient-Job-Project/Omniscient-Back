package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.ProfileDTO;
import com.omniscient.omniscientback.mypage.model.Profile;
import com.omniscient.omniscientback.mypage.repository.ProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

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

    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = convertToEntity(profileDTO);
        profile.setStatus(true);
        Profile savedProfile = profileRepository.save(profile);
        return convertToDTO(savedProfile);
    }

    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        Profile existingProfile = profileRepository.findByIdAndStatusTrue(profileDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));
        updateProfileFields(existingProfile, profileDTO);
        Profile updatedProfile = profileRepository.save(existingProfile);
        return convertToDTO(updatedProfile);
    }

    public void deactivateProfile(Integer id) {
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("활성화된 프로필을 찾을 수 없습니다"));
        profile.setStatus(false);
        profileRepository.save(profile);
    }

    private ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        BeanUtils.copyProperties(profile, dto);
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