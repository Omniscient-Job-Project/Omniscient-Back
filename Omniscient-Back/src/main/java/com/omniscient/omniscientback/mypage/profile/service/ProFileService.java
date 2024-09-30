package com.omniscient.omniscientback.mypage.profile.service;

import com.omniscient.omniscientback.api.categoryapi.model.CategoryEntity;
import com.omniscient.omniscientback.mypage.profile.model.ProFileDTO;
import com.omniscient.omniscientback.mypage.profile.model.ProFileEntity;
import com.omniscient.omniscientback.mypage.profile.repository.ProFileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProFileService {

    private final ProFileRepository proFileRepository;


    @Autowired
    public ProFileService(ProFileRepository proFileRepository) {
        this.proFileRepository = proFileRepository;
    }

    public ProFileEntity saveProfile(ProFileDTO profileDTO) {
        // DTO를 엔티티로 변환
        ProFileEntity profileEntity = new ProFileEntity();
        profileEntity.setProfileName(profileDTO.getProfileName());
        profileEntity.setProfilePosition(profileDTO.getProfilePosition());
        profileEntity.setProfileEmail(profileDTO.getProfileEmail());
        profileEntity.setProfilePhone(profileDTO.getProfilePhone());
        profileEntity.setProfileAge(profileDTO.getProfileAge());
        profileEntity.setProfileAddress(profileDTO.getProfileAddress());
        profileEntity.setProfileimageFileName(profileDTO.getProfileimageFileName());
        profileEntity.setProfileactive(profileDTO.getProfileactive());

        // 엔티티를 DB에 저장
        return proFileRepository.save(profileEntity);
    }

    // 프로필 전체 조회
    public List<ProFileEntity> getAllProfiles() {
        return proFileRepository.findAll();
    }

    // 프로필 ID로 조회
    public Optional<ProFileEntity> getProfileById(Integer id) {
        return proFileRepository.findById(id);
    }

    // 프로필 생성
    public ProFileEntity createProfile(ProFileEntity proFileEntity) {
        return proFileRepository.save(proFileEntity);
    }

    // 프로필 업데이트
    public ProFileEntity updateProfile(Integer id, ProFileDTO proFileDTO) {
        ProFileEntity proFileEntity = new ProFileEntity();

        proFileEntity.setProfileName(proFileDTO.getProfileName());
        proFileEntity.setProfilePosition(proFileDTO.getProfilePosition());
        proFileEntity.setProfileEmail(proFileDTO.getProfileEmail());
        proFileEntity.setProfilePhone(proFileDTO.getProfilePhone());
        proFileEntity.setProfilePhone(proFileDTO.getProfilePhone());
        proFileEntity.setProfileAge(proFileDTO.getProfileAge());
        proFileEntity.setProfileAddress(proFileDTO.getProfileAddress());
        proFileEntity.setProfileimageFileName(proFileDTO.getProfileimageFileName());
        proFileEntity.setProfileactive(proFileDTO.getProfileactive());

        proFileEntity = proFileRepository.save(proFileEntity);
        return proFileEntity;

    }


    @Transactional
    public void deleteProFileById(Integer id) {
        Optional<ProFileEntity> optionalProFile = proFileRepository.findById(id);

        if (optionalProFile.isPresent()) {
            ProFileEntity proFileEntity = optionalProFile.get();
            // status를 'deleted'로 변경
            proFileEntity.setStatus("deleted");

            // 변경 사항을 저장
            proFileRepository.save(proFileEntity);
        } else {
            throw new EntityNotFoundException("ProFile not found with id: " + id);
        }
    }
}
