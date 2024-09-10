package com.omniscient.omniscientback.api.JobabaAPI.service;

import com.omniscient.omniscientback.api.JobabaAPI.model.JobabaDTO;
import com.omniscient.omniscientback.api.JobabaAPI.model.JobabaEntity;
import com.omniscient.omniscientback.api.JobabaAPI.repository.JobabaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobabaService {

    private final JobabaRepository jobababRepository;

    @Autowired
    public JobabaService(JobabaRepository jobababRepository) {
        this.jobababRepository = jobababRepository;
    }

    public void saveJob(JobabaDTO jobabaDTO) {
        JobabaEntity jobabaEntity = new JobabaEntity();

        jobabaEntity.setJobabaCompanyName(jobabaDTO.getJobabaCompanyName());
        jobabaEntity.setJobabaInfoTitle(jobabaDTO.getJobabaInfoTitle());
        jobabaEntity.setJobabaWageType(jobabaDTO.getJobabaWageType());
        jobabaEntity.setJobabaSalary(jobabaDTO.getJobabaSalary());
        jobabaEntity.setJobabaLocation(jobabaDTO.getJobabaLocation());
        jobabaEntity.setJobabaEmploymentType(jobabaDTO.getJobabaEmploymentType());
//        jobabaEntity.setJobabaPostedDate(jobabaDTO.getJobabaPostedDate());
//        jobabaEntity.setJobabaClosingDate(jobabaDTO.getJobabaClosingDate());
        jobabaEntity.setJobabaWebInfoUrl(jobabaDTO.getJobabaWebInfoUrl());
        jobabaEntity.setJobabaMobileInfoUrl(jobabaDTO.getJobabaMobileInfoUrl());
        jobabaEntity.setJobabaCareerCondition(jobabaDTO.getJobabaCareerCondition());

        jobababRepository.save(jobabaEntity);


    }

    // 취업 정보에 저장된 테이블을 다 불러오는 거

    public List<JobabaEntity> getAllJobs() {
        return jobababRepository.findAll();
    }

    public Optional<JobabaEntity> getJobabaById(Integer id) {
        return jobababRepository.findById(id);
    }
}
