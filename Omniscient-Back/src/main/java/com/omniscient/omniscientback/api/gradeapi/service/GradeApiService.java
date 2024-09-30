package com.omniscient.omniscientback.api.gradeapi.service;

import com.omniscient.omniscientback.api.gradeapi.model.GradeDTO;
import com.omniscient.omniscientback.api.gradeapi.model.GradeEntity;
import com.omniscient.omniscientback.api.gradeapi.repository.GradeApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeApiService {

    private final GradeApiRepository gradeApiRepository;

    @Autowired
    public GradeApiService(GradeApiRepository gradeApiRepository) {
        this.gradeApiRepository = gradeApiRepository;
    }

    public void saveGradeJob(GradeDTO gradeDTO) {
        try {
            GradeEntity gradeEntity = convertToEntity(gradeDTO);
            gradeApiRepository.save(gradeEntity);
        } catch (Exception e) {
            // 로그를 남기거나 예외 처리를 추가
            e.printStackTrace();
            throw new RuntimeException("Error saving GradeJob", e);
        }
    }

    public List<GradeEntity> getAllGrade() {
        return gradeApiRepository.findAll();
    }

    public Optional<GradeEntity> getGradeById(Integer gradeId) {
        return gradeApiRepository.findById(gradeId);
    }

    private GradeEntity convertToEntity(GradeDTO gradeDTO) {
        GradeEntity gradeEntity = new GradeEntity();

        gradeEntity.setNumOfRows(gradeDTO.getNumOfRows());
        gradeEntity.setResultCode(gradeDTO.getResultCode());
        gradeEntity.setResultMsg(gradeDTO.getResultMsg());
        gradeEntity.setGrdNm(gradeDTO.getGrdNm());
        gradeEntity.setInstiNm(gradeDTO.getInstiNm());
        gradeEntity.setJmNm(gradeDTO.getJmNm());
        gradeEntity.setPreyyAcquQualIncRate(gradeDTO.getPreyyAcquQualIncRate());
        gradeEntity.setPreyyQualAcquCnt(gradeDTO.getPreyyQualAcquCnt());
        gradeEntity.setQualAcquCnt(gradeDTO.getQualAcquCnt());
        gradeEntity.setQualAcquRank(gradeDTO.getQualAcquRank());
        gradeEntity.setStatisYy(gradeDTO.getStatisYy());
        gradeEntity.setSumYy(gradeDTO.getSumYy());
        gradeEntity.setTotalCount(gradeDTO.getTotalCount());

        return gradeEntity;
    }
}
