package com.omniscient.omniscientback.api.jobApi.service;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobabaEntity;
import com.omniscient.omniscientback.api.jobApi.repository.JobabaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobabaService {

    private final JobabaRepository jobabaRepository;
    private static final Logger logger = LoggerFactory.getLogger(JobabaService.class); // Logger 초기화


    @Autowired
    public JobabaService(JobabaRepository jobabaRepository) {
        this.jobabaRepository = jobabaRepository;
    }

    @Transactional
    public boolean saveJob(JobTotalDTO jobDTO) {
        JobabaEntity jobabaEntity = new JobabaEntity();

        jobabaEntity.setJobabaCompanyName(jobDTO.getCompanyName());
        jobabaEntity.setJobabaInfoTitle(jobDTO.getInfoTitle());
        jobabaEntity.setJobabaWageType(jobDTO.getWageType());
        jobabaEntity.setJobabaSalary(jobDTO.getSalary());
        jobabaEntity.setJobabaLocation(jobDTO.getLocation());
        jobabaEntity.setJobabaEmploymentType(jobDTO.getEmploymentType());
        jobabaEntity.setJobabaCareerCondition(jobDTO.getCareerCondition());

        // 날짜 변환
        jobabaEntity.setJobabaPostedDate(jobDTO.getPostedDate());  // LocalDate로 저장
        jobabaEntity.setJobabaClosingDate(jobDTO.getClosingDate());  // LocalDate로 저장

        try {
            jobabaRepository.save(jobabaEntity); // 저장 시도
            logger.info("잡아바 정보 저장 성공: {}", jobabaEntity.getJobabaCompanyName()); // 성공 로그
            return true; // 저장 성공 시 true 반환
        } catch (Exception e) {
            logger.error("잡아바 정보 저장 실패: {}", e.getMessage()); // 실패 로그
            return false; // 저장 실패 시 false 반환
        }
    }

    public List<JobabaEntity> getAllJobs() {
        logger.info("모든 잡아바 정보 조회 요청"); // 로그 추가
        return jobabaRepository.findAll();
    }

    public Optional<JobabaEntity> getJobById(String id) {
        logger.info("ID {}로 잡아바 정보 조회 요청", id); // 로그 추가
        return jobabaRepository.findById(Integer.parseInt(id));
    }
}