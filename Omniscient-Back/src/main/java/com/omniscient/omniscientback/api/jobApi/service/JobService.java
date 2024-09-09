package com.omniscient.omniscientback.api.jobApi.service;

import com.omniscient.omniscientback.api.jobApi.model.JobDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobEntity;
import com.omniscient.omniscientback.api.jobApi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;


    public void saveJob(JobDTO jobDTO) {
        JobEntity jobEntity = new JobEntity();

        jobEntity.setJobCompanyName(jobDTO.getJobCompanyName());
        jobEntity.setJobInfoTitle(jobDTO.getJobInfoTitle());
        jobEntity.setJobWageType(jobDTO.getJobWageType());
        jobEntity.setJobSalary(jobDTO.getJobSalary());
        jobEntity.setJobLocation(jobDTO.getJobLocation());
        jobEntity.setJobEmploymentType(jobDTO.getJobEmploymentType());
//        jobEntity.setJobPostedDate(jobDTO.getJobPostedDate());
//        jobEntity.setJobClosingDate(jobDTO.getJobClosingDate());
        jobEntity.setJobWebInfoUrl(jobDTO.getJobWebInfoUrl());
        jobEntity.setJobMobileInfoUrl(jobDTO.getJobMobileInfoUrl());
        jobEntity.setJobCareerCondition(jobDTO.getJobCareerCondition());

        jobRepository.save(jobEntity);


    }

    // 취업 정보에 저장된 테이블을 다 불러오는 거

    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<JobEntity> getJobById(Integer id) {
        return jobRepository.findById(id);
    }
}
