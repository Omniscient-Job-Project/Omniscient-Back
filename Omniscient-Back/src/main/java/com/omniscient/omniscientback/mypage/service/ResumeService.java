package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.ResumeDTO;
import com.omniscient.omniscientback.mypage.model.Resume;
import com.omniscient.omniscientback.mypage.repository.ResumeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return resumeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResumeDTO getResume(Integer id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        return convertToDTO(resume);
    }

    public ResumeDTO createResume(ResumeDTO resumeDTO) {
        Resume resume = convertToEntity(resumeDTO);
        Resume savedResume = resumeRepository.save(resume);
        return convertToDTO(savedResume);
    }

    public ResumeDTO updateResume(Integer id, ResumeDTO resumeDTO) {
        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        BeanUtils.copyProperties(resumeDTO, existingResume, "id");
        Resume updatedResume = resumeRepository.save(existingResume);
        return convertToDTO(updatedResume);
    }

    public void deleteResume(Integer id) {
        resumeRepository.deleteById(id);
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