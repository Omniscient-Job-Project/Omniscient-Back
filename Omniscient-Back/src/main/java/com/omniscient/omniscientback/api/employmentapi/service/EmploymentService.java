package com.omniscient.omniscientback.api.employmentapi.service;

import com.omniscient.omniscientback.api.employmentapi.model.EmploymentDTO;
import com.omniscient.omniscientback.api.employmentapi.model.EmploymentEntity;
import com.omniscient.omniscientback.api.employmentapi.repository.EmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmploymentService {

    private final EmploymentRepository employmentRepository;

    @Autowired
    public EmploymentService(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    @Transactional
    public void saveWoman(EmploymentDTO employmentDTO) {

        // DTO의 데이터를 Entity로 매핑
        EmploymentEntity employmentEntity = new EmploymentEntity();

        employmentEntity.setRefineLotnoAddr(employmentDTO.getRefineLotnoAddr());
        employmentEntity.setRefineZipNo(employmentDTO.getRefineZipNo());
        employmentEntity.setContctNm(employmentDTO.getContctNm());
        employmentEntity.setInstNm(employmentDTO.getInstNm());
        employmentEntity.setRefineRoadnmAddr(employmentDTO.getRefineRoadnmAddr());
        employmentEntity.setRefineWgs84Lat(employmentDTO.getRefineWgs84Lat());
        employmentEntity.setDivNm(employmentDTO.getDivNm());
        employmentEntity.setRegionNm(employmentDTO.getRegionNm());
        employmentEntity.setHmpgNm(employmentDTO.getHmpgNm());
        employmentEntity.setRefineWgs84Logt(employmentDTO.getRefineWgs84Logt());


        // Repository를 사용하여 데이터 저장
        employmentRepository.save(employmentEntity);
    }


    public List<EmploymentEntity> getAllWoman() {
        return employmentRepository.findAll();
    }

    public Optional<EmploymentEntity> getWomanById(Integer id) {
        return employmentRepository.findById(id);
    }

}
