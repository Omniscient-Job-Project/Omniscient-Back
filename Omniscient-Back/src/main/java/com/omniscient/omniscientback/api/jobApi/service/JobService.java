package com.omniscient.omniscientback.api.jobApi.service;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobEntity;
import com.omniscient.omniscientback.api.jobApi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void saveJob(JobTotalDTO jobDTO) {
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

        jobRepository.save(jobEntity);
    }

    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<JobEntity> getJobById(Integer id) {
        return jobRepository.findById(id);
    }
}
