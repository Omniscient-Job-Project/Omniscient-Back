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
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProfileDTO getProfile(Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid profile Id:" + id));
        return convertToDTO(profile);
    }

    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = convertToEntity(profileDTO);
        Profile savedProfile = profileRepository.save(profile);
        return convertToDTO(savedProfile);
    }

    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(profileDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid profile Id:" + profileDTO.getId()));

        BeanUtils.copyProperties(profileDTO, profile, "id");
        Profile updatedProfile = profileRepository.save(profile);
        return convertToDTO(updatedProfile);
    }

    public void deleteProfile(Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid profile Id:" + id));
        profileRepository.delete(profile);
    }

    private ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        BeanUtils.copyProperties(profile, profileDTO);
        return profileDTO;
    }

    private Profile convertToEntity(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        BeanUtils.copyProperties(profileDTO, profile);
        return profile;
    }
}