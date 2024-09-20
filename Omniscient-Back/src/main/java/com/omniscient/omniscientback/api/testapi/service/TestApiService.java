package com.omniscient.omniscientback.api.testapi.service;

import com.omniscient.omniscientback.api.testapi.model.TestDTO;
import com.omniscient.omniscientback.api.testapi.model.TestEntity;
import com.omniscient.omniscientback.api.testapi.repository.TestApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        validateTestDTO(testDTO);

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

    private void validateTestDTO(TestDTO testDTO) {
        if (testDTO.getImplSeq() == null || testDTO.getImplSeq().isEmpty()) {
            throw new IllegalArgumentException("ImplSeq는 null 또는 빈 값입니다.");
        }

        // 날짜 형식 검증 예제 (YYYY-MM-DD)
        validateDate(testDTO.getDocRegStartDt(), "DocRegStartDt");
        validateDate(testDTO.getDocRegEndDt(), "DocRegEndDt");
        validateDate(testDTO.getDocExamStartDt(), "DocExamStartDt");
        validateDate(testDTO.getDocExamEndDt(), "DocExamEndDt");
        validateDate(testDTO.getPracRegStartDt(), "PracRegStartDt");
        validateDate(testDTO.getPracRegEndDt(), "PracRegEndDt");
        validateDate(testDTO.getPracExamStartDt(), "PracExamStartDt");
        validateDate(testDTO.getPracExamEndDt(), "PracExamEndDt");
        validateDate(testDTO.getPracPassDt(), "PracPassDt");

        // 다른 필드에 대한 검증 추가
    }

    private void validateDate(String date, String fieldName) {
        if (date == null || date.isEmpty()) {
            return; // 빈 날짜 필드는 무시 또는 처리 논리에 따라 조정 가능
        }
        try {
            java.time.LocalDate.parse(date); // ISO_LOCAL_DATE 형식 (YYYY-MM-DD)으로 파싱 시도
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(fieldName + " 유효한 날짜 형식이 아닙니다.");
        }
    }
}
