package com.omniscient.omniscientback.api.testapi.service;

import com.omniscient.omniscientback.api.testapi.model.TestDTO;
import com.omniscient.omniscientback.api.testapi.model.TestEntity;
import com.omniscient.omniscientback.api.testapi.repository.TestApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TestApiService {
    private static final Logger logger = LoggerFactory.getLogger(TestApiService.class);
    private final TestApiRepository testApiRepository;

    @Autowired
    public TestApiService(TestApiRepository testApiRepository) {
        this.testApiRepository = testApiRepository;
    }


    public void saveTestJob(TestDTO testDTO){
        try {
            logger.info("TestDTO를 Entity로 변환 중: " + testDTO.toString());
            TestEntity testEntity = convertToEntity(testDTO);
            logger.info("TestEntity 저장 중: " + testEntity.toString());
            testApiRepository.save(testEntity);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error saving test job");
        }
    }

    public List<TestEntity> getAllTest(){
        return testApiRepository.findAll();
    }

    public Optional<TestEntity> getTestById(Integer id){
        return testApiRepository.findById(id);
    }

    private TestEntity convertToEntity(TestDTO testDTO){
        TestEntity testEntity = new TestEntity();

        testEntity.setNumOfRows(testDTO.getNumOfRows());
        testEntity.setPageNo(testDTO.getPageNo());
        testEntity.setDataFormat(testDTO.getDataFormat());
        testEntity.setImplYy(testDTO.getImplYy());
        testEntity.setResultCode(testDTO.getResultCode());
        testEntity.setResultMsg(testDTO.getResultMsg());
        testEntity.setImplSeq(testDTO.getImplSeq());
        testEntity.setQualgbNm(testDTO.getQualgbNm());
        testEntity.setDescription(testDTO.getDescription());

        testEntity.setDocRegStartDt(testDTO.getDocRegStartDt());
        testEntity.setDocRegEndDt(testDTO.getDocRegEndDt());

        testEntity.setDocExamStartDt(testDTO.getDocExamStartDt());
        testEntity.setDocExamEndDt(testDTO.getDocExamEndDt());

        testEntity.setPracRegStartDt(testDTO.getPracRegStartDt());
        testEntity.setPracRegEndDt(testDTO.getPracRegEndDt());

        testEntity.setPracExamStartDt(testDTO.getPracExamStartDt());
        testEntity.setPracExamEndDt(testDTO.getPracExamEndDt());

        testEntity.setPracPassDt(testDTO.getPracPassDt());
        testEntity.setTotalCount(testDTO.getTotalCount());

        return testEntity;
    }
}
