package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.ResumeDTO;
import com.omniscient.omniscientback.mypage.model.Resume;
import com.omniscient.omniscientback.mypage.repository.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public ResumeDTO getResume(Integer id) {
        logger.info("ID {}인 이력서 조회 시작", id);
        Resume resume = resumeRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> {
                    logger.warn("ID {}인 이력서를 찾을 수 없음", id);
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + id);
                });
        logger.info("ID {}인 이력서 조회 완료", id);
        return convertToDTO(resume);
    }

    public ResumeDTO createResume(ResumeDTO resumeDTO) {
        logger.info("새로운 이력서 생성 시작");
        Resume resume = convertToEntity(resumeDTO);
        resume.setStatus(true);
        Resume savedResume = resumeRepository.save(resume);
        logger.info("새로운 이력서 생성 완료. ID: {}", savedResume.getId());
        return convertToDTO(savedResume);
    }

    public ResumeDTO updateResume(Integer id, ResumeDTO resumeDTO) {
        logger.info("이력서 업데이트 시작. ID: {}", id);
        Resume existingResume = resumeRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> {
                    logger.warn("업데이트할 이력서를 찾을 수 없음. ID: {}", id);
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + id);
                });
        BeanUtils.copyProperties(resumeDTO, existingResume, "id", "status");
        Resume updatedResume = resumeRepository.save(existingResume);
        logger.info("이력서 업데이트 완료. ID: {}", id);
        return convertToDTO(updatedResume);
    }

    public void deactivateResume(Integer id) {
        logger.info("이력서 비활성화 시작. ID: {}", id);
        Resume resume = resumeRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> {
                    logger.warn("비활성화할 이력서를 찾을 수 없음. ID: {}", id);
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + id);
                });
        resume.setStatus(false);
        resumeRepository.save(resume);
        logger.info("이력서 비활성화 완료. ID: {}", id);
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