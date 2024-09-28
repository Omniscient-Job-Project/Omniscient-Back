package com.omniscient.omniscientback.mypage.profile.service;

import com.omniscient.omniscientback.api.categoryapi.model.CategoryEntity;
import com.omniscient.omniscientback.mypage.profile.model.ProFileDTO;
import com.omniscient.omniscientback.mypage.profile.model.ProFileEntity;
import com.omniscient.omniscientback.mypage.profile.repository.ProFileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
