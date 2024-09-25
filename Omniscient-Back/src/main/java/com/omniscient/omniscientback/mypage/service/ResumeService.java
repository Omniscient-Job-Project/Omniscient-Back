package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.ResumeDTO;
import com.omniscient.omniscientback.mypage.model.Resume;
import com.omniscient.omniscientback.mypage.repository.ResumeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public List<ResumeDTO> getAllResumes() {
        List<ResumeDTO> resumes = resumeRepository.findAllByStatusTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return resumes;
    }

    public ResumeDTO getResume(Integer resumeId) {
        Resume resume = resumeRepository.findByResumeIdAndStatusTrue(resumeId)
                .orElseThrow(() -> {
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + resumeId);
                });
        return convertToDTO(resume);
    }

    @Transactional
    public ResumeDTO createResume(ResumeDTO resumeDTO) {
        Resume resume = convertToEntity(resumeDTO);
        resume.setStatus(true);
        Resume savedResume = resumeRepository.save(resume);
        return convertToDTO(savedResume);
    }

    @Transactional
    public ResumeDTO updateResume(Integer resumeId, ResumeDTO resumeDTO) {
        Resume existingResume = resumeRepository.findByResumeIdAndStatusTrue(resumeId)
                .orElseThrow(() -> {
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + resumeId);
                });

        // 여기서 "id"를 "resumeId"로 변경
        BeanUtils.copyProperties(resumeDTO, existingResume, "resumeId", "status");

        Resume updatedResume = resumeRepository.save(existingResume);
        return convertToDTO(updatedResume);
    }

    @Transactional
    public void deactivateResume(Integer resumeId) {
        Resume resume = resumeRepository.findByResumeIdAndStatusTrue(resumeId)
                .orElseThrow(() -> {
                    return new RuntimeException("이력서를 찾을 수 없습니다: ID " + resumeId);
                });
        resume.setStatus(false);
        resumeRepository.save(resume);
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
