package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.ResumeDTO;
import com.omniscient.omniscientback.mypage.model.Resume;
import com.omniscient.omniscientback.mypage.repository.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public List<ResumeDTO> getAllResumes() {
        logger.info("모든 활성화된 이력서 조회 시작");
        List<ResumeDTO> resumes = resumeRepository.findAllByStatusTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        logger.info("활성화된 이력서 조회 완료. 총 {}개 조회됨", resumes.size());
        return resumes;
    }

    public ResumeDTO getResume(Integer resumeId) {
        logger.info("ID {}인 이력서 조회 시작", resumeId);
        Resume resume = resumeRepository.findByIdAndStatusTrue(resumeId)
                .orElseThrow(() -> {
                    logger.warn("ID {}인 이력서를 찾을 수 없음", resumeId);
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + resumeId);
                });
        logger.info("ID {}인 이력서 조회 완료", resumeId);
        return convertToDTO(resume);
    }

    @Transactional
    public ResumeDTO createResume(ResumeDTO resumeDTO) {
        logger.info("새로운 이력서 생성 시작");
        Resume resume = convertToEntity(resumeDTO);
        resume.setStatus(true);
        Resume savedResume = resumeRepository.save(resume);
        logger.info("새로운 이력서 생성 완료. ID: {}", savedResume.getResumeId());
        return convertToDTO(savedResume);
    }

    @Transactional
    public ResumeDTO updateResume(Integer resumeId, ResumeDTO resumeDTO) {
        logger.info("이력서 업데이트 시작. ID: {}", resumeId);
        Resume existingResume = resumeRepository.findByIdAndStatusTrue(resumeId)
                .orElseThrow(() -> {
                    logger.warn("업데이트할 이력서를 찾을 수 없음. ID: {}", resumeId);
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + resumeId);
                });

        // 여기서 "id"를 "resumeId"로 변경
        BeanUtils.copyProperties(resumeDTO, existingResume, "resumeId", "status");

        Resume updatedResume = resumeRepository.save(existingResume);
        logger.info("이력서 업데이트 완료. ID: {}", resumeId);
        return convertToDTO(updatedResume);
    }

    @Transactional
    public void deactivateResume(Integer resumeId) {
        logger.info("이력서 비활성화 시작. ID: {}", resumeId);
        Resume resume = resumeRepository.findByIdAndStatusTrue(resumeId)
                .orElseThrow(() -> {
                    logger.warn("비활성화할 이력서를 찾을 수 없음. ID: {}", resumeId);
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + resumeId);
                });
        resume.setStatus(false);
        resumeRepository.save(resume);
        logger.info("이력서 비활성화 완료. ID: {}", resumeId);
    }

    private ResumeDTO convertToDTO(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        BeanUtils.copyProperties(resume, dto);
        return dto;
    }

    private Resume convertToEntity(ResumeDTO dto) {
        Resume resume = new Resume();
        BeanUtils.copyProperties(dto, resume);
        return resume;
    }
}
