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
        jobabaEntity.setJobabaCareerCondition(jobabaDTO.getJobabaCareerCondition());

        // 날짜 변환
        jobabaEntity.setJobabaPostedDate(jobabaDTO.getJobabaPostedDate());  // LocalDate로 저장
        jobabaEntity.setJobabaClosingDate(jobabaDTO.getJobabaClosingDate());  // LocalDate로 저장

        jobabaEntity.setJobabaWebInfoUrl(jobabaDTO.getJobabaWebInfoUrl());
        jobabaEntity.setWorkRegionCdCont(jobabaDTO.getWorkRegionCdCont());
        jobabaEntity.setRecrutFieldCdNm(jobabaDTO.getRecrutFieldCdNm());
        jobabaEntity.setRecrutFieldNm(jobabaDTO.getRecrutFieldNm());
        jobabaEntity.setEmplmntPsncnt(jobabaDTO.getEmplmntPsncnt());

        jobababRepository.save(jobabaEntity);
    }

    public List<JobabaEntity> getAllJobs() {
        return jobababRepository.findAll();
    }

    public Optional<JobabaEntity> getJobabaById(Integer id) {
        return jobababRepository.findById(id);
    }
}
