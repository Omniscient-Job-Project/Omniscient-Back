package com.omniscient.omniscientback.api.testapi.service;

import com.omniscient.omniscientback.api.testapi.model.TestDTO;
import com.omniscient.omniscientback.api.testapi.model.TestEntity;
import com.omniscient.omniscientback.api.testapi.repository.TestApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class TestApiService {
    private final TestApiRepository testApiRepository;

    @Autowired
    public TestApiService(TestApiRepository testApiRepository) {
        this.testApiRepository = testApiRepository;
    }

    public void saveTestJob(TestDTO testDTO) {

        TestEntity testEntity = convertToEntity(testDTO);

        // 중복 체크
        if (testApiRepository.existsById(Integer.valueOf(testEntity.getImplSeq()))) {
            throw new RuntimeException("동일한 ImplSeq를 가진 엔티티가 이미 존재");
        }

        testApiRepository.save(testEntity);
    }

    public List<TestEntity> getAllTest() {
        return testApiRepository.findAll();
    }

    public Optional<TestEntity> getTestById(Integer id) {
        return testApiRepository.findById(id);
    }

    private TestEntity convertToEntity(TestDTO testDTO) {
        TestEntity testEntity = new TestEntity();

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
