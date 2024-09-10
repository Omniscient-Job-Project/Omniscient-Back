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

    /**
     * 모든 활성화된 이력서를 조회합니다.
     * @return 활성화된 이력서 목록
     */
    public List<ResumeDTO> getAllResumes() {
        return resumeRepository.findAllByStatusTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID의 활성화된 이력서를 조회합니다.
     * @param id 조회할 이력서의 ID
     * @return 조회된 이력서 DTO
     * @throws RuntimeException 이력서를 찾을 수 없는 경우
     */
    public ResumeDTO getResume(Integer id) {
        Resume resume = resumeRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("이력서를 찾을 수 없습니다"));
        return convertToDTO(resume);
    }

    /**
     * 새로운 이력서를 생성합니다.
     * @param resumeDTO 생성할 이력서 정보
     * @return 생성된 이력서 DTO
     */
    public ResumeDTO createResume(ResumeDTO resumeDTO) {
        Resume resume = convertToEntity(resumeDTO);
        resume.setStatus(true);
        Resume savedResume = resumeRepository.save(resume);
        return convertToDTO(savedResume);
    }

    /**
     * 기존 이력서를 업데이트합니다.
     * @param id 업데이트할 이력서의 ID
     * @param resumeDTO 업데이트할 이력서 정보
     * @return 업데이트된 이력서 DTO
     * @throws RuntimeException 이력서를 찾을 수 없는 경우
     */
    public ResumeDTO updateResume(Integer id, ResumeDTO resumeDTO) {
        Resume existingResume = resumeRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("이력서를 찾을 수 없습니다"));
        BeanUtils.copyProperties(resumeDTO, existingResume, "id", "status");
        Resume updatedResume = resumeRepository.save(existingResume);
        return convertToDTO(updatedResume);
    }

    /**
     * 이력서를 비활성화합니다. (삭제 대신 상태 변경)
     * @param id 비활성화할 이력서의 ID
     * @throws RuntimeException 이력서를 찾을 수 없는 경우
     */
    public void deactivateResume(Integer id) {
        Resume resume = resumeRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("이력서를 찾을 수 없습니다"));
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