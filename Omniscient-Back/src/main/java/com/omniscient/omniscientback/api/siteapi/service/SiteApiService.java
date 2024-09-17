package com.omniscient.omniscientback.api.siteapi.service;

import com.omniscient.omniscientback.api.siteapi.model.SiteDTO;
import com.omniscient.omniscientback.api.siteapi.model.SiteEntity;
import com.omniscient.omniscientback.api.siteapi.repository.SiteApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteApiService {

    private final SiteApiRepository siteApiRepository;

    @Autowired
    public SiteApiService(SiteApiRepository siteApiRepository) {
        this.siteApiRepository = siteApiRepository;
    }

    public void saveSiteJob(SiteDTO siteDTO){
        try {
            SiteEntity siteEntity = convertToEntity(siteDTO);
            siteApiRepository.save(siteEntity);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error while saving data", e);
        }
    }

    public List<SiteEntity> getAllGrade(){
        return siteApiRepository.findAll();
    }

    public Optional<SiteEntity> getSiteById(Integer id){
        return siteApiRepository.findById(id);
    }

    private SiteEntity convertToEntity(SiteDTO siteDTO){
        SiteEntity siteEntity = new SiteEntity();

        siteEntity.setNumOfRows(siteDTO.getNumOfRows());
        siteEntity.setPageNo(siteDTO.getPageNo());
        siteEntity.setBrchCd(siteDTO.getBrchCd());
        siteEntity.setAddress(siteDTO.getAddress());
        siteEntity.setBrchNm(siteDTO.getBrchNm());
        siteEntity.setExamAreaGbNm(siteDTO.getExamAreaGbNm());
        siteEntity.setExamAreaNm(siteDTO.getExamAreaNm());
        siteEntity.setPlceLoctGid(siteDTO.getPlceLoctGid());
        siteEntity.setTelNo(siteDTO.getTelNo());
        siteEntity.setResultCode(siteDTO.getResultCode());
        siteEntity.setResultMsg(siteDTO.getResultMsg());
        siteEntity.setTotalCount(siteDTO.getTotalCount());

        return siteEntity;
    }
}
