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

    /**
     * 모든 활성화된 프로필을 조회합니다.
     * @return 활성화된 프로필 목록
     */
    public List<ProfileDTO> getAllProfiles() {
        // status가 true인 프로필만 반환하도록 수정
        return profileRepository.findAllByStatusTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID의 활성화된 프로필을 조회합니다.
     * @param id 조회할 프로필의 ID
     * @return 조회된 프로필 DTO
     * @throws IllegalArgumentException 프로필을 찾을 수 없는 경우
     */
    public ProfileDTO getProfile(Integer id) {
        // status가 true인 프로필만 조회하도록 수정
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다"));
        return convertToDTO(profile);
    }

    /**
     * 새로운 프로필을 생성합니다.
     * @param profileDTO 생성할 프로필 정보
     * @return 생성된 프로필 DTO
     */
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = convertToEntity(profileDTO);
        // 새로운 프로필 생성 시 status를 true로 설정
        profile.setStatus(true);
        Profile savedProfile = profileRepository.save(profile);
        return convertToDTO(savedProfile);
    }

    /**
     * 기존 프로필을 업데이트합니다.
     * @param profileDTO 업데이트할 프로필 정보
     * @return 업데이트된 프로필 DTO
     * @throws IllegalArgumentException 프로필을 찾을 수 없는 경우
     */
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        // status가 true인 프로필만 업데이트할 수 있도록 수정
        Profile existingProfile = profileRepository.findByIdAndStatusTrue(profileDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다"));
        BeanUtils.copyProperties(profileDTO, existingProfile, "id", "status");
        Profile updatedProfile = profileRepository.save(existingProfile);
        return convertToDTO(updatedProfile);
    }

    /**
     * 프로필을 비활성화합니다. (삭제 대신 상태 변경)
     * @param id 비활성화할 프로필의 ID
     * @throws IllegalArgumentException 프로필을 찾을 수 없는 경우
     */
    public void deactivateProfile(Integer id) {
        Profile profile = profileRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다"));
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
}