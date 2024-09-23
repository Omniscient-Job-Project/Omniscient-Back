package com.omniscient.omniscientback.api.jobApi.service;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobEntity;
import com.omniscient.omniscientback.api.jobApi.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private static final Logger logger = LoggerFactory.getLogger(JobService.class); // Logger 초기화

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional
    public boolean saveJob(JobTotalDTO jobDTO) {
        JobEntity jobEntity = new JobEntity();

        jobEntity.setJobCompanyName(jobDTO.getCompanyName());
        jobEntity.setJobInfoTitle(jobDTO.getInfoTitle());
        jobEntity.setJobWageType(jobDTO.getWageType());
        jobEntity.setJobSalary(jobDTO.getSalary());
        jobEntity.setJobLocation(jobDTO.getLocation());
        jobEntity.setJobEmploymentType(jobDTO.getEmploymentType());
//        jobEntity.setJobPostedDate(jobDTO.getPostedDate());
//        jobEntity.setJobClosingDate(jobDTO.getClosingDate());
        jobEntity.setJobWebInfoUrl(jobDTO.getWebInfoUrl());
        jobEntity.setJobMobileInfoUrl(jobDTO.getMobileInfoUrl());
        jobEntity.setJobCareerCondition(jobDTO.getCareerCondition());

        try {
            jobRepository.save(jobEntity); // 저장 시도
            logger.info("Job saved successfully: {}", jobEntity.getJobCompanyName()); // 성공 로그
            return true; // 저장 성공 시 true 반환
        } catch (Exception e) {
            logger.error("Error saving job: {}", e.getMessage()); // 실패 로그
            return false; // 저장 실패 시 false 반환
        }
    }

    public List<JobEntity> getAllJobs() {
        logger.info("Retrieving all jobs"); // 로그 추가
        return jobRepository.findAll();
    }

    public Optional<JobEntity> getJobById(Integer id) {
        logger.info("Retrieving job with ID: {}", id); // 로그 추가
        return jobRepository.findById(id);
    }
}